package com.example.demo.util;

import com.example.demo.annotation.DisableResponseWrapper;
import com.example.demo.annotation.EnableResponseDTOWrapper;
import com.example.demo.annotation.EnableResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@ControllerAdvice(annotations = EnableResponseWrapper.class)
public class ResponseWrapperAdvice implements ResponseBodyAdvice<Object> {
    private final IWrapperService wrapperService;

    /**
     * Метод не будет обработан, если помечен аннотацией {@link DisableResponseWrapper} <br/> <br/>
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return {@code true} if {@link #beforeBodyWrite} should be invoked;
     * {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter returnType, @NonNull Class converterType) {
        for (Annotation a : returnType.getMethodAnnotations()) {
            if (a.annotationType() == DisableResponseWrapper.class) {
                return false;
            }
        }

        return true;
    }

    /**
     * Оборачиваем ответ
     *
     * @param body                  the body to be written
     * @param returnType            the return type of the controller method
     * @param selectedContentType   the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request               the current request
     * @param response              the current response
     * @return the body that was passed in or a modified (possibly new) instance
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(@Nullable Object body, @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType, @NonNull Class selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        return body == null ? null : wrapModel(getBody(body, returnType), returnType);
    }

    private Object getBody(Object body, MethodParameter returnType) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<?> dtoClass = getDtoClass(returnType);

        if (dtoClass != null) {

            if (body instanceof Collection<?>) {
                body = ((Collection<?>) body).stream().map(e -> {
                    try {
                        Object instance = dtoClass.getDeclaredConstructor().newInstance();
                        BeanUtils.copyProperties(e, instance);
                        return instance;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException instantiationException) {
                        throw new RuntimeException("Error create instance");
                    }
                }).collect(Collectors.toList());
            } else {
                Object instance = dtoClass.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(body, instance);
                body = instance;
            }
        }
        return body;
    }

    private Class<?> getDtoClass(MethodParameter returnType) {
        for (Annotation annotation : returnType.getContainingClass().getAnnotations()) {
            if (annotation.annotationType() == EnableResponseDTOWrapper.class) {
                return ((EnableResponseDTOWrapper) annotation).wrapperClass();
            }
        }
        return null;
    }

    private Object wrapModel(Object body, MethodParameter returnType) {
        // получаем wrapperClass из аннотации
        Class<? extends IWrapperModel> wrapperClass = null;
        for (Annotation annotation : returnType.getContainingClass().getAnnotations()) {
            if (annotation.annotationType() == EnableResponseWrapper.class) {
                wrapperClass = ((EnableResponseWrapper) annotation).wrapperClass();
                break;
            }
        }

        if (wrapperClass == null) {
            return body;
        }

        // проверяем, был ли передан Collection или наследник Collection
        if (Collection.class.isAssignableFrom(body.getClass())) {
            try {
                Collection<?> bodyCollection = (Collection<?>) body;

                // проверяем, что collection не пустой
                if (bodyCollection.isEmpty()) {
                    return body;
                }
                // оборачиваем каждый элемент коллекции
                return generateListOfResponseWrapper(bodyCollection, wrapperClass);
            } catch (Exception e) {
                return body;
            }
        }

        // если не collection
        return generateResponseWrapper(body, wrapperClass);
    }

    /**
     * Генерируем список оберток для коллекции (те информация добавляется внутрь списка)
     *
     * @param bodyCollection список объектов, которые необходимо обернуть
     * @param wrapperClass   объект обертки
     * @return список оберток
     */
    private List<IWrapperModel> generateListOfResponseWrapper(Collection<?> bodyCollection, Class<? extends IWrapperModel> wrapperClass) {
        return bodyCollection.stream()
                .map((t) -> t == null ?
                        null :
                        generateResponseWrapper(t, wrapperClass)
                )
                .collect(Collectors.toList());
    }

    /**
     * Генерируем обертку вокруг объекта
     *
     * @param body         объект который необходимо поместить в обертку
     * @param wrapperClass объект обертки
     * @return обертка
     */
    @SneakyThrows
    private IWrapperModel generateResponseWrapper(Object body, Class<? extends IWrapperModel> wrapperClass) {
        // wrapperClass должен иметь конструктор без параметров - получаем объект IWrapperModel
        IWrapperModel wrapper = wrapperClass.getDeclaredConstructor().newInstance();
        wrapper.setBody(body);
        wrapper.setData(wrapperService.getData(body));
        return wrapper;
    }

}