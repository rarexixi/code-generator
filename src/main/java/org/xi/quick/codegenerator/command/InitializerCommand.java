package org.xi.quick.codegenerator.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.xi.quick.codegenerator.entity.ValidStatusField;
import org.xi.quick.codegenerator.staticdata.StaticConfigData;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 郗世豪（xish@cloud-young.com）
 * @date 2018/01/01 10:58
 */
@Component
@Order(1)
public class InitializerCommand implements CommandLineRunner {

    @Value("${field.status.valid}")
    private String validStatusField;

    @Value("${field.status.valid.value}")
    private String statusFieldValidValue;

    @Value("${field.status.invalid.value}")
    private String statusFieldInvalidValue;

    @Value("${field.not_required}")
    private String notRequiredField;

    @Override
    public void run(String... strings) throws Exception {

        StaticConfigData.VALID_STATUS_FIELD = getValidStatusField();
        StaticConfigData.NOT_REQUIRED_FIELD_SET = getNotRequiredFieldSets();
    }

    private ValidStatusField getValidStatusField() {

        ValidStatusField field = new ValidStatusField();
        field.setFieldName(validStatusField);
        field.setValidValue(statusFieldValidValue);
        field.setInvalidValue(statusFieldInvalidValue);

        return field;
    }

    private Set<String> getNotRequiredFieldSets() {
        return Arrays.asList(notRequiredField.split(",")).stream()
                .collect(Collectors.toSet());
    }
}
