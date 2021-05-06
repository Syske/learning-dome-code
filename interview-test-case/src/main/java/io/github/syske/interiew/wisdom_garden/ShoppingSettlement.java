package io.github.syske.interiew.wisdom_garden;

import io.github.syske.interiew.wisdom_garden.entity.CouponInfo;
import io.github.syske.interiew.wisdom_garden.entity.GoodsInfo;
import io.github.syske.interiew.wisdom_garden.entity.PromotionInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: interview-test-case
 * @description: 购物结算
 * @author: syske
 * @create: 2021-02-27 11:02
 */
public class ShoppingSettlement {
    private static ShoppingSettlement shoppingSettlement = new ShoppingSettlement();

    /**
     * 产品目录
     * 电子：ipad，iphone，显示器，笔记本电脑，键盘
     * 食品：面包，饼干，蛋糕，牛肉，鱼，蔬菜
     * 日用品：餐巾纸，收纳箱，咖啡杯，雨伞
     * 酒类：啤酒，白酒，伏特加
     */
    private static final Map<String, List<String>> goodsMap = new HashMap<String, List<String>>();

    // 初始化产品目录
    static {
        // 电子产品
        List<String> ecGoodsList = new ArrayList<String>(5);
        ecGoodsList.add("ipad");
        ecGoodsList.add("iphone");
        ecGoodsList.add("显示器");
        ecGoodsList.add("笔记本电脑");
        ecGoodsList.add("键盘");
        goodsMap.put("电子", ecGoodsList);
        // 食品
        List<String> foodGoodsList = new ArrayList<String>(6);
        foodGoodsList.add("面包");
        foodGoodsList.add("饼干");
        foodGoodsList.add("蛋糕");
        foodGoodsList.add("牛肉");
        foodGoodsList.add("鱼");
        foodGoodsList.add("蔬菜");
        goodsMap.put("食品", foodGoodsList);
        // 日用品
        List<String> dailyGoodsList = new ArrayList<String>(4);
        dailyGoodsList.add("餐巾纸");
        dailyGoodsList.add("收纳箱");
        dailyGoodsList.add("咖啡杯");
        dailyGoodsList.add("雨伞");
        goodsMap.put("日用品", dailyGoodsList);
        // 酒类
        List<String> wineGoodsList = new ArrayList<String>(3);
        wineGoodsList.add("啤酒");
        wineGoodsList.add("白酒");
        wineGoodsList.add("伏特加");
        goodsMap.put("酒类", wineGoodsList);

    }

    /**
     * 项目主入口
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入促销信息：");
        // 读取促销信息
        String promotionInfoStr = null;
        List<PromotionInfo> promotionInfoList = new ArrayList<PromotionInfo>();
        while (scanner.hasNextLine()) {
            promotionInfoStr = scanner.nextLine();
            if (isEmpty(promotionInfoStr)) {
                break;
            }
            PromotionInfo promotionInfo = shoppingSettlement.getPromotionInfo(promotionInfoStr);
            promotionInfoList.add(promotionInfo);
        }
        // 读取商品信息
        System.out.println("请输入购物商品信息：");
        String goodsInfoStr = null;
        List<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();
        while (scanner.hasNextLine()) {
            goodsInfoStr = scanner.nextLine();
            if (isEmpty(goodsInfoStr)) {
                break;
            }
            GoodsInfo goodsInfo = shoppingSettlement.getGoodsInfo(goodsInfoStr, promotionInfoList);
            goodsInfoList.add(goodsInfo);
        }
        // 获取结算日期
        System.out.println("请输入结算日期：");
        String settlementDateStr = scanner.nextLine();
        // 获取优惠券信息
        System.out.println("请输入优惠券信息：");
        String couponInfoStr = scanner.nextLine();
        CouponInfo couponInfo = shoppingSettlement.getCouponInfo(couponInfoStr);
        // 结算购物费用
        double totalAmount = shoppingSettlement.calculateTotalAmount(goodsInfoList, couponInfo, settlementDateStr);
        System.out.println("本次消费应付金额：" + String.format("%.2f %n", totalAmount));
    }

    /**
     * 计算总金额
     *
     * @param goodsInfoList
     * @param couponInfo
     * @param settlementDate
     * @return
     * @throws ParseException
     */
    public double calculateTotalAmount(List<GoodsInfo> goodsInfoList,
                                       CouponInfo couponInfo, String settlementDate) throws ParseException {        // 总金额
        double totalAmount = 0.0;
        Iterator<GoodsInfo> iterator = goodsInfoList.iterator();
        while (iterator.hasNext()) {
            GoodsInfo next = iterator.next();
            PromotionInfo promotionInfo = next.getPromotionInfo();
            // 商品促销信息不为空，且促销当天结算
            if (promotionInfo != null && getCountDownDays(promotionInfo.getDate(), settlementDate) == 0) {
                totalAmount += next.getCount() * next.getPrice() * promotionInfo.getDiscount();
            } else {
                totalAmount += next.getCount() * next.getPrice();
            }
        }

        // 优惠券有效，且大于优惠券满减金额，方能使用优惠券
        if (couponInfo != null && getCountDownDays(couponInfo.getDate(), settlementDate) >= 0
                && totalAmount >= couponInfo.getBottomLineAmount()) {
            totalAmount -= couponInfo.getDiscountAmount();
        }

        return totalAmount;
    }

    /**
     * 获取商品折扣信息
     *
     * @param goodsTypeName     商品种类
     * @param promotionInfoList 促销信息
     * @return
     */
    private PromotionInfo getGoodsPromotionInfo(String goodsTypeName,
                                                List<PromotionInfo> promotionInfoList) {
        for (PromotionInfo promotionInfo : promotionInfoList) {
            if (goodsTypeName.equals(promotionInfo.getGoodsTypeName())) {
                return promotionInfo;
            }
        }
        return null;
    }

    /**
     * 获取商品分类名称
     *
     * @param goodsName
     * @return
     */
    private String getGoodsTypeName(String goodsName) {
        Iterator<String> iterator = goodsMap.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            List<String> goodsList = goodsMap.get(next);
            if (goodsList.contains(goodsName)) {
                return next;
            }
        }
        return "NULL";
    }

    /**
     * 获取商品信息
     *
     * @param goodsInfoStr
     * @return
     */
    public GoodsInfo getGoodsInfo(String goodsInfoStr, List<PromotionInfo> promotionInfoList) {
        String pattern = "(^\\d+)(\\s\\D\\s)(\\D+)(\\s:\\s)(\\d+.\\d+)";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(goodsInfoStr);
        if (m.find()) {
            // 商品名称
            String goodsName = m.group(3).trim();
            // 商品数量
            String goodsCount = m.group(1).trim();
            // 商品单价
            String goodsPrice = m.group(5).trim();
            // 商品类型名称
            String goodsTypeName = getGoodsTypeName(goodsName);
            // 商品折扣信息
            PromotionInfo goodsPromotionInfo = getGoodsPromotionInfo(goodsTypeName, promotionInfoList);
            return new GoodsInfo(Integer.parseInt(goodsCount), goodsName,
                    Double.parseDouble(goodsPrice), goodsPromotionInfo, goodsTypeName);
        }
        return new GoodsInfo();
    }

    /**
     * 获取优惠券信息
     *
     * @param couponInfoStr
     * @return
     */
    public CouponInfo getCouponInfo(String couponInfoStr) {
        String pattern = "(^\\d+.\\d+.\\d+)(\\s\\d+\\s)(\\d+)";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(couponInfoStr);
        if (m.find()) {
            return new CouponInfo(m.group(1).trim(),
                    Double.parseDouble(m.group(2).trim()),
                    Double.parseDouble(m.group(3).trim()));
        }
        return null;
    }

    /**
     * 获取促销信息
     *
     * @param promotionInfoStr
     * @return
     * @throws Exception
     */
    public PromotionInfo getPromotionInfo(String promotionInfoStr) throws Exception {
        String pattern = "^[0-9]{4}.[0-9]{2}.[0-9]{2}\\s\\|\\s\\d+.\\d+\\s\\|\\s\\D+";
        boolean matches = Pattern.matches(pattern, promotionInfoStr);
        if (matches) {
            String[] splits = promotionInfoStr.split("\\|");
            // 促销信息日期
            String date = splits[0].trim();
            // 折扣
            String discount = splits[1].trim();
            // 商品类别
            String goodsClassName = splits[2].trim();
            return new PromotionInfo(date, Double.parseDouble(discount), goodsClassName);
        }
        return new PromotionInfo();
    }

    /**
     * 获取传入日期日期间隔天数
     *
     * @param targetDateStr  目标日期，格式 yyyy-MM-dd
     * @param settlementDate 结算日期，格式 yyyy-MM-dd
     * @return 返回天数
     */
    public static int getCountDownDays(String targetDateStr, String settlementDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        //算两个日期间隔多少天
        Date nowDate = format.parse(settlementDate);
        Date targetDate = format.parse(targetDateStr); // 目标日期
        int days = (int) ((targetDate.getTime() - nowDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    private static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
