package ru.practicum.model;

public class CourierResponse {
    // Для ответа при создании курьера
    private Boolean ok;

    // Для ответа при логине курьера
    private Integer id;

    public Boolean isOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}