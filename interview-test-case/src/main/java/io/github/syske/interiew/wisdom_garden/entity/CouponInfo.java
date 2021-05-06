package io.github.syske.interiew.wisdom_garden.entity;

/**
 * @program: interview-test-case
 * @description: 优惠券
 * @author: syske
 * @create: 2021-02-27 12:16
 */
public class CouponInfo {
    // 有效日期
    private String date;
    // 满减金额
    private double bottomLineAmount;
    // 扣减金额
    private double discountAmount;

    public CouponInfo() {
    }

    public CouponInfo(String date, double bottomLineAmount, double discountAmount) {
        this.date = date;
        this.bottomLineAmount = bottomLineAmount;
        this.discountAmount = discountAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getBottomLineAmount() {
        return bottomLineAmount;
    }

    public void setBottomLineAmount(double bottomLineAmount) {
        this.bottomLineAmount = bottomLineAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public String toString() {
        return "CouponInfo{" +
                "date='" + date + '\'' +
                ", bottomLineAmount=" + bottomLineAmount +
                ", discountAmount=" + discountAmount +
                '}';
    }
}
