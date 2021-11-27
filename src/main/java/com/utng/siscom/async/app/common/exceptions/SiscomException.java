package com.utng.siscom.async.app.common.exceptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SiscomException extends RuntimeException {
    private final List<String> errors = new ArrayList<>();

    public SiscomException(String message, String... errors) {
        super(message);
        if (errors != null && errors.length > 0) {
            Collections.addAll(this.errors, errors);
        }
    }

    public SiscomException(String message, List<String> errors) {
        super(message);
        this.errors.addAll(errors);
    }

    public List<String> getErrors() {
        return errors;
    }
}
