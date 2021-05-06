package io.github.syske.interiew.wisdom_garden.entity;

/**
 * @program: interview-test-case
 * @description: 促销信息
 * @author: syske
 * @create: 2021-02-25 22:37
 */
public class PromotionInfo {
    // 促销日期
    private String date;
    // 折扣
    private double discount;
    // 商品类别
    private String goodsTypeName;

    public PromotionInfo() {
    }

    public PromotionInfo(String date, double discount, String goodsClassName) {
        this.date = date;
        this.discount = discount;
        this.goodsTypeName = goodsClassName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }

    @Override
    public String toString() {
        return "PromotionInfo{" +
                "date='" + date + '\'' +
                ", discount=" + discount +
                ", goodsTypeName='" + goodsTypeName + '\'' +
                '}';
    }
}
