package com.example.store.models;

public class SearchForm {
    private String name;

    private Integer minPrice;

    private Integer maxPrice;

    private String priceIncrement;

    private Boolean available=false;

    public String toQuery() {
        String query = "from Product where";
        if (! this.name.isEmpty()) query = query.concat(" ( name like '%" + name + "' or name like '%" + name + "%' or name like '" + name + "%' ) and");
        query = query.concat(" price >= " + this.minPrice);
        query = query.concat(" and price <= " + this.maxPrice);
        if (this.available) query = query.concat(" and quantity > 0");
        System.out.println(this.priceIncrement);
        if (this.priceIncrement.equals("inc")) query = query.concat(" order by price asc");
        if (this.priceIncrement.equals("dec")) query = query.concat(" order by price desc");
        return query;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getPriceIncrement() {
        return priceIncrement;
    }

    public void setPriceIncrement(String priceIncrement) {
        this.priceIncrement = priceIncrement;
    }
}
