package io.github.syske.interiew.wisdom_garden.entity;

/**
 * @program: interview-test-case
 * @description: 商品信息
 * @author: syske
 * @create: 2021-02-27 11:59
 */
public class GoodsInfo {

    // 数量
    private int count;
    // 名称
    private String name;
    // 价格
    private double price;
    // 折扣
    private PromotionInfo promotionInfo;
    // 商品类别
    private String typeName;

    public GoodsInfo() {
        this.count = 0;
        this.price = 0.0;
    }

    public GoodsInfo(int count, String name, double price, PromotionInfo promotionInfo, String typeName) {
        this.count = count;
        this.name = name;
        this.price = price;
        this.promotionInfo = promotionInfo;
        this.typeName = typeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PromotionInfo getPromotionInfo() {
        return promotionInfo;
    }

    public void setPromotionInfo(PromotionInfo promotionInfo) {
        this.promotionInfo = promotionInfo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "count=" + count +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", promotionInfo=" + promotionInfo +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
