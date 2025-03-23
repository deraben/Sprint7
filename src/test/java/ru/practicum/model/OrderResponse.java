package ru.practicum.model;

import java.util.List;

public class OrderResponse {
    // Для ответа при создании заказа
    private Integer track;

    // Для ответа при получении списка заказов
    private List<Order> orders;
    private PageInfo pageInfo;
    private List<AvailableStation> availableStations;

    public Integer getTrack() {
        return track;
    }

    public void setTrack(Integer track) {
        this.track = track;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AvailableStation> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<AvailableStation> availableStations) {
        this.availableStations = availableStations;
    }

    public static class PageInfo {
        private int page;
        private int total;
        private int limit;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
    }

    public static class AvailableStation {
        private String name;
        private String number;
        private String color;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}