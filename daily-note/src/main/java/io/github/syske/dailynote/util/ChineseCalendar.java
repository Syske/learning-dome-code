package io.github.syske.dailynote.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: liqiang
 * @Date: 2019/5/27 11:54
 * @Description: 计算农历节假日信息
 *         SimpleCalendar.Element element=SimpleCalendar.getCalendarDetail("2019-06-06","yyyy-MM-dd");
 *         element=SimpleCalendar.getCalendarDetail("2019-06-04","yyyy-MM-dd");
 */
public class ChineseCalendar {
    long[] lunarInfo = new long[]{
            0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0, 0x55d2,
            0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd255, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977,
            0x497f, 0xa4b0, 0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970,
            0x6566, 0xd4a0, 0xea50, 0x6a95, 0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f,
            0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2, 0xa950, 0xb557,
            0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0,
            0xaea6, 0xab50, 0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0,
            0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4, 0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6,
            0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46, 0xab60, 0x9570,
            0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58, 0x5ac0, 0xab60, 0x96d5, 0x92e0,
            0xc960, 0xd954, 0xd4a0, 0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5,
            0xa950, 0xb4a0, 0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930,
            0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260, 0xea65, 0xd530,
            0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520, 0xdd45,
            0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0,
            0x4b63, 0x937f, 0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef,
            0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55, 0x56a0, 0xa6d0, 0x55d4,
            0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50, 0x55a0, 0xaba4, 0xa5b0, 0x52b0,
            0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260,
            0xe968, 0xd520, 0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252,
            0xd520};
    List<Element> elements=new ArrayList<Element>();
    public static  Map<String,ChineseCalendar> cache=new HashMap<String,ChineseCalendar>();
    long[] solarMonth = new long[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
    String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
    String[] solarTerm = new String[]{"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
    int[] sTermInfo = new int[]{0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758};
    char[] nStr1 = new char[]{'日', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'};
    String[] nStr2 = new String[]{"初", "十", "廿", "卅", " "};

    static String[] monthChinese=new String[]{ "正月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "冬月", "腊月" };
    static  String []dayChinese=new String[]{"初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十", "十一", "十二","十三","十四","十五","十六","十七","十八","十九","二十","廿一","廿二","廿三","廿四","廿五","廿六","廿七","廿八","廿九","三十","卅一"};
    char[] jcName0 = new char[]{'建', '除', '满', '平', '定', '执', '破', '危', '成', '收', '开', '闭'};
    char[] jcName1 = new char[]{'闭', '建', '除', '满', '平', '定', '执', '破', '危', '成', '收', '开'};
    char[] jcName2 = new char[]{'开', '闭', '建', '除', '满', '平', '定', '执', '破', '危', '成', '收'};
    char[] jcName3 = new char[]{'收', '开', '闭', '建', '除', '满', '平', '定', '执', '破', '危', '成'};
    char[] jcName4 = new char[]{'成', '收', '开', '闭', '建', '除', '满', '平', '定', '执', '破', '危'};
    char[] jcName5 = new char[]{'危', '成', '收', '开', '闭', '建', '除', '满', '平', '定', '执', '破'};
    char[] jcName6 = new char[]{'破', '危', '成', '收', '开', '闭', '建', '除', '满', '平', '定', '执'};
    char[] jcName7 = new char[]{'执', '破', '危', '成', '收', '开', '闭', '建', '除', '满', '平', '定'};
    char[] jcName8 = new char[]{'定', '执', '破', '危', '成', '收', '开', '闭', '建', '除', '满', '平'};
    char[] jcName9 = new char[]{'平', '定', '执', '破', '危', '成', '收', '开', '闭', '建', '除', '满'};
    char[] jcName10 = new char[]{'满', '平', '定', '执', '破', '危', '成', '收', '开', '闭', '建', '除'};
    char[] jcName11 = new char[]{'除', '满', '平', '定', '执', '破', '危', '成', '收', '开', '闭', '建'};

    //国历节日  *表示放假日
    String[] sFtv = new String[]{
            "0101*元旦",
            "0106  中国13亿人口日",
            "0110  中国110宣传日",

            "0202  世界湿地日",
            "0204  世界抗癌症日",
            "0210  世界气象日",
            "0214  情人节",
            "0221  国际母语日",
            "0207  国际声援南非日",

            "0303  全国爱耳日",
            "0308  妇女节",
            "0312  植树节 孙中山逝世纪念日",
            "0315  消费者权益保护日",
            "0321  世界森林日",
            "0322  世界水日",
            "0323  世界气象日",
            "0324  世界防治结核病日",

            "0401  愚人节",
            "0407  世界卫生日",
            "0422  世界地球日",

            "0501*国际劳动节",
            "0504  中国青年节",
            "0505  全国碘缺乏病日",
            "0508  世界红十字日",
            "0512  国际护士节",
            "0515  国际家庭日",
            "0517  世界电信日",
            "0518  国际博物馆日",
            "0519  中国汶川地震哀悼日 全国助残日",
            "0520  全国学生营养日",
            "0522  国际生物多样性日",
            "0523  国际牛奶日",
            "0531  世界无烟日",

            "0601  国际儿童节",
            "0605  世界环境日",
            "0606  全国爱眼日",
            "0617  防治荒漠化和干旱日",
            "0623  国际奥林匹克日",
            "0625  全国土地日",
            "0626  国际反毒品日",

            "0701  建党节 香港回归纪念日",
            "0707  抗日战争纪念日",
            "0711  世界人口日",

            "0801  八一建军节",
            "0815  日本正式宣布无条件投降日",

            "0908  国际扫盲日",
            "0909  **逝世纪念日",
            "0910  教师节",
            "0916  国际臭氧层保护日",
            "0917  国际和平日",
            "0918  九·一八事变纪念日",
            "0920  国际爱牙日",
            "0927  世界旅游日",
            "0928  孔子诞辰",

            "1001*国庆节 国际音乐节 国际老人节",
            "1002  国际减轻自然灾害日",
            "1004  世界动物日",
            "1007  国际住房日",
            "1008  世界视觉日 全国高血压日",
            "1009  世界邮政日",
            "1010  辛亥革命纪念日 世界精神卫生日",
            "1015  国际盲人节",
            "1016  世界粮食节",
            "1017  世界消除贫困日",
            "1022  世界传统医药日",
            "1024  联合国日",
            "1025  人类天花绝迹日",
            "1026  足球诞生日",
            "1031  万圣节",

            "1107  十月社会主义革命纪念日",
            "1108  中国记者日",
            "1109  消防宣传日",
            "1110  世界青年节",
            "1112  孙中山诞辰",
            "1114  世界糖尿病日",
            "1117  国际大学生节",

            "1201  世界艾滋病日",
            "1203  世界残疾人日",
            "1209  世界足球日",
            "1210  世界人权日",
            "1212  西安事变纪念日",
            "1213  南京大屠杀",
            "1220  澳门回归纪念日",
            "1221  国际篮球日",
            "1224  平安夜",
            "1225  圣诞节 世界强化免疫日",
            "1226  **诞辰"};
    //农历节日  *表示放假日
    String[] lFtv = new String[]{
            "0101*春节",
            "0102*大年初二",
            "0103*大年初三",
            "0104*大年初四",
            "0105*大年初五",
            "0106*大年初六",
            "0107*大年初七",
            "0105  路神生日",
            "0115  元宵节",
            "0202  龙抬头",
            "0219  观世音圣诞",
            "0404  寒食节",
            "0408  佛诞节 ",
            "0505*端午节",
            "0606  天贶节 姑姑节",
            "0624  彝族火把节",
            "0707  七夕情人节",
            "0714  鬼节(南方)",
            "0715  盂兰节",
            "0730  地藏节",
            "0815*中秋节",
            "0909  重阳节",
            "1001  祭祖节",
            "1117  阿弥陀佛圣诞",
            "1208  腊八节 释迦如来成道日",
            "1223  过小年",
            "1229*腊月二十九",
            "0100*除夕"};
    //某月的第几个星期几; 5,6,7,8 表示到数第 1,2,3,4 个星期几
    String[] wFtv = new String[]{
            "0110  黑人节",
            "0150  世界麻风日",
            "0121  日本成人节",
            "0520  母亲节",
            "0530  全国助残日",
            "0630  父亲节",
            "0716  合作节",
            "0730  被奴役国家周",
            "0932  国际和平日",
            "0940  国际聋人节 世界儿童日",
            "1011  国际住房日",
            "1144  感恩节"};
    private Long length;//公历当月天数
    private int firstWeek;  //公历当月1日星期几
    public static Element getCalendarDetail(Date date) throws ParseException {


        Calendar cal = Calendar.getInstance() ;
        cal.setTime(date);
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        String cacheKey=(year+"-"+month);
        ChineseCalendar lunarCalendarUtil=null;
        if(false){
            lunarCalendarUtil=cache.get(cacheKey);
        }else {
            lunarCalendarUtil=new ChineseCalendar();
            lunarCalendarUtil.calendar(year, month);
            cache.put(cacheKey,lunarCalendarUtil);
        }

        return lunarCalendarUtil.getElements().get(cal.get(Calendar.DATE)-1);
    }

    public static Element getCalendarDetail(String date,String pattern) throws ParseException {
        SimpleDateFormat df2 = new SimpleDateFormat(pattern);
        return getCalendarDetail(df2.parse(date));
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public void calendar(int y, int m) throws ParseException {
        Lunar lDObj = null;
        Boolean lL=null;
        Long lD2=null;
        Integer lY = null, lM=null, lD = 1, lX = 0, tmp1, tmp2, lM2, lY2=null, tmp3, dayglus, bsg, xs, xs1, fs, fs1, cs, cs1=null;
        String cY, cM, cD; //年柱,月柱,日柱
        Integer[] lDPOS = new Integer[3];
        Integer n = 0;
        Integer firstLM = 0;
        String dateString = y + "-" +(m+1) + "-" + 1;
        Date sDObj = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sDObj);
        this.length = solarDays(y, m);    //公历当月天数
        this.firstWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;     //公历当月1日星期几

        ////////年柱 1900年立春后为庚子年(60进制36)
        if (m < 2) cY = cyclical(y - 1900 + 36 - 1);
        else cY = cyclical(y - 1900 + 36);
        int term2 = sTerm(y, 2); //立春日期

        ////////月柱 1900年1月小寒以前为 丙子月(60进制12)
        int firstNode = sTerm(y, m * 2);//返回当月「节」为几日开始
        cM = cyclical((y - 1900) * 12 + m + 12);

        lM2 = (y - 1900) * 12 + m + 12;
        //当月一日与 1900/1/1 相差天数
        //1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df2.parse("" + y + "-" + (m+1) + "-" + 1 + " 00:00:00");

        long dayCyclical = date.getTime() / 86400000 + 25567 + 10;
        //// long dayCyclical =date.getTime() / 86400000 + 25567 + 10;
        SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (int i = 0; i < this.length; i++) {
            if(i==18){
                int b=5;
            }
            if (lD > lX) {
                sDObj= df3.parse("" + y + "-" + (m+1) + "-" + (i+1) + " 00:00:00");   //当月一日日期
                lDObj = new Lunar(sDObj);     //农历
                lY = lDObj.year;           //农历年
                lM = lDObj.month;          //农历月
                lD = lDObj.day;            //农历日
                lL = lDObj.isLeap;         //农历是否闰月
                lX = lL ? leapDays(lY) : monthDays(lY, lM); //农历当月最后一天

                if (n == 0) firstLM = lM;

                lDPOS[n++] = i - lD + 1;
            }

            //依节气调整二月分的年柱, 以立春为界
            if (m == 1 && (i + 1) == term2) {
                cY = cyclical(y - 1900 + 36);
                lY2 = (y - 1900 + 36);
            }
            //依节气月柱, 以「节」为界
            if ((i + 1) == firstNode) {
                cM = cyclical((y - 1900) * 12 + m + 13);
                lM2 = (y - 1900) * 12 + m + 13;
            }
            //日柱
            cD = cyclical(dayCyclical + i);
            lD2 = (dayCyclical + i);
            Element element = new Element(y, m + 1, i + 1, (nStr1[(i + this.firstWeek) % 7]),
                    lY, lM, lD++, lL,
                    cY, cM, cD, cAnimal(y));
//            element.setcDay(cDay(element.getlDay()));
            int paramterLy2=lY2==null?-1:(lY2 % 12);
            int paramterLm2=lM2==null?-1:lM2 % 12;
            long paramterLd2=lD2==null?-1:lD2 % 12;
            int paramterLy2b=lY2==null?-1:lY2 % 10;
            int paramterLy2c= (int) (lD2==null?-1:lD2 % 10);
            int paramterLld=lD==null?-1:lD - 1;
            element.setSgz5(CalConv2(paramterLy2, paramterLm2, (int) paramterLd2, paramterLy2b,paramterLy2c , lM,paramterLld , m + 1, cs1==null?-1:cs1));
            element.setSgz3(cyclical6(lM2 % 12, (int) ((lD2) % 12)));
            elements.add(element);


        }

        //节气
        tmp1 = sTerm(y, m * 2) - 1;
        tmp2 = sTerm(y, m * 2 + 1) - 1;
        elements.get(tmp1).solarTerms = solarTerm[m * 2];
        elements.get(tmp2).solarTerms = solarTerm[m * 2 + 1];
        if (m == 3) elements.get(tmp1).color = "red"; //清明颜色

        Pattern p = Pattern.compile("^(\\d{2})(\\d{2})([\\s\\*])(.+)$");
        //国历节日
        for (String i : sFtv){
            Matcher matcher=p.matcher(i);
            if (matcher.matches()) {
                if(i.equals("1212  西安事变纪念日")){
                    int j=2;
                }
                if (Integer.valueOf(matcher.group(1)).intValue() == (m + 1)) {
                    elements.get(Integer.valueOf(matcher.group(2)) - 1).solarFestival +=matcher.group(4)+"";
                    if (matcher.group(3).equals('*'))   elements.get(Integer.valueOf(matcher.group(0)) - 1).color = "red";
                }
            }
        }

        p = Pattern.compile("^(\\d{2})(.{2})([\\s\\*])(.+)$");
        //农历节日
        for (String i  :  lFtv){
            Matcher matcher=p.matcher(i);
            if (matcher.matches()) {
                tmp1 = Integer.valueOf(matcher.group(1)) - firstLM;
                if (tmp1 == -11) tmp1 = 1;
                if (tmp1 >= 0 && tmp1 < n) {
                    tmp2 = lDPOS[tmp1] +Integer.valueOf(matcher.group(2)) - 1;
                    if (tmp2 >= 0 && tmp2 < this.length) {
                        elements.get(tmp2).lunarFestival += matcher.group(4);
                        if (matcher.group(3).equals("*")) elements.get(tmp2).color = "red";
                    }
                }
            }
        }

        //复活节只出现在3或4月
        if (m == 2 || m == 3) {
            Easter estDay = new Easter(y);
            if (m == estDay.m)
                elements.get(estDay.d - 1).solarFestival = elements.get(estDay.d - 1).solarFestival + " 复活节(Easter Sunday)";
        }


        //黑色星期五
        if ((this.firstWeek + 12) % 7 == 5)
            elements.get(12).solarFestival += "黑色星期五";

        //今日
        //if (y == tY && m == tM) this[tD - 1].isToday = true;
    }
    //==============================返回公历 y年某m+1月的天数
    public long solarDays(int y, int m) {
        if (m == 1)
            return(((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0)) ? 29 : 28);
        else
            return(solarMonth[m]);
    }
    //============================== 返回阴历 (y年,m+1月)
    public char cyclical6(int num, int num2) {
        if (num == 0) return(jcName0[num2]);
        if (num == 1) return(jcName1[num2]);
        if (num == 2) return(jcName2[num2]);
        if (num == 3) return(jcName3[num2]);
        if (num == 4) return(jcName4[num2]);
        if (num == 5) return(jcName5[num2]);
        if (num == 6) return(jcName6[num2]);
        if (num == 7) return(jcName7[num2]);
        if (num == 8) return(jcName8[num2]);
        if (num == 9) return(jcName9[num2]);
        if (num == 10) return(jcName10[num2]);
        if (num == 11) return(jcName11[num2]);
        return '0';
    }
    public String  CalConv2(int yy,int  mm,int dd,int y,int d,int m, int dt,int  nm,int nd) {
        int dy = d  + dd;
        if ((yy == 0 && dd == 6) || (yy == 6 && dd == 0) || (yy == 1 && dd == 7) || (yy == 7 && dd == 1) || (yy == 2 && dd == 8) || (yy == 8 && dd == 2) || (yy == 3 && dd == 9) || (yy == 9 && dd == 3) || (yy == 4 && dd == 10) || (yy == 10 && dd == 4) || (yy == 5 && dd == 11) || (yy == 11 && dd == 5)) {
            return "日值岁破 大事不宜";
        }
        else if ((mm == 0 && dd == 6) || (mm == 6 && dd == 0) || (mm == 1 && dd == 7) || (mm == 7 && dd == 1) || (mm == 2 && dd == 8) || (mm == 8 && dd == 2) || (mm == 3 && dd == 9) || (mm == 9 && dd == 3) || (mm == 4 && dd == 10) || (mm == 10 && dd == 4) || (mm == 5 && dd == 11) || (mm == 11 && dd == 5)) {
            return "日值月破 大事不宜";
        }
        else if ((y == 0 && dy == 911) || (y == 1 && dy == 55) || (y == 2 && dy == 111) || (y == 3 && dy == 75) || (y == 4 && dy == 311) || (y == 5 && dy == 9) || (y == 6 && dy == 511) || (y == 7 && dy == 15) || (y == 8 && dy == 711) || (y == 9 && dy == 35)) {
            return "日值上朔 大事不宜";
        }
        else if ((m == 1 && dt == 13) || (m == 2 && dt == 11) || (m == 3 && dt == 9) || (m == 4 && dt == 7) || (m == 5 && dt == 5) || (m == 6 && dt == 3) || (m == 7 && dt == 1) || (m == 7 && dt == 29) || (m == 8 && dt == 27) || (m == 9 && dt == 25) || (m == 10 && dt == 23) || (m == 11 && dt == 21) || (m == 12 && dt == 19)) {
            return "日值杨公十三忌 大事不宜";
        }
        else {
            return "";
        }
    }
    //    public Date getUtcDate(String dateStr){
//        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date date = df2.parse("1900-01-06 02:05:00");
//    }
    //============================== 传入 offsenew Datet 返回干支, 0=甲子
    public String cyclical(long num) {
        return(Gan[(int) (num % 10)] + Zhi[(int) (num % 12)]);
    }

    /**
     * 返回年份对应的生肖
     *
     * @param year
     * @return
     */
    public String cAnimal(int year) {
        int i = (year - 1900) % 12;
        if(i == 0)
            return Animals[12-1];
        return Animals[i -1];
    }
    //======================  中文日期
    public String cDay(int d) {
        String  s;

        switch (d) {
            case  10:
                s = "初十";  break;
            case  20:
                s = "二十";  break;
            case  30:
                s = "三十";  break;
            default  :
                s = nStr2[Double.valueOf(Math.floor(d / 10)).intValue()];
                s += nStr1[d % 10];
        }
        return(s);
    }
    //===== 某年的第n个节气为几日(从0小寒起算)
    public int sTerm(int  y,int  n) throws ParseException {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df2.parse("1900-01-06 02:05:00");
        Long utcTime2=date.getTime();
        BigDecimal time2=new BigDecimal(31556925974.7).multiply(new BigDecimal(y - 1900)).add(new BigDecimal( sTermInfo[n]).multiply(BigDecimal.valueOf(60000L)));
        BigDecimal time=time2.add(BigDecimal.valueOf(utcTime2));
        Date offDate = new Date(time.longValue());
        Calendar cal = Calendar.getInstance() ;
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTime(offDate);
        int utcDate=cal.get(Calendar.DATE);
        //日期从0算起
        return utcDate;
    }
    //====================================== 返回农历 y年闰哪个月 1-12 , 没闰返回 0
    public Long  leapMonth(int y) {
        long lm = lunarInfo[y - 1900] & 0xf;
        return(lm == 0xf ? 0 : lm);
    }
    //====================================== 返回农历 y年的总天数
    public Long lYearDays(int y) {
        long i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) sum += (lunarInfo[y - 1900] & i)!=0 ? 1 : 0;
        return(sum + leapDays(y));
    }

    //====================================== 返回农历 y年闰月的天数
    public int leapDays(int y) {
        if (leapMonth(y)!=0) return( (lunarInfo[y - 1899] & 0xf) == 0xf ? 30 : 29);
        else return 0;
    }
    //====================================== 返回农历 y年m月的总天数
    private int monthDays(int y,int m) {
        return( (lunarInfo[y - 1900] & (0x10000 >> m))!=0 ? 30 : 29 );
    }

    public   class Lunar{
        private  int year;
        private boolean isLeap;
        private  int month;
        private  int day;
        public Lunar(Date objDate) throws ParseException {
            int i, leap = 0, temp = 0;
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            df2.setTimeZone(TimeZone.getTimeZone("UTC"));
            DateFormat dtFmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = df2.parse(  dtFmt.format(objDate));
            SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            df3.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date3 = df3.parse("" + 1900 + "-" + 1 + "-" + 31 + " 00:00:00");
            long time1=date.getTime();
            long time2=date3.getTime();
            int offset = (int)(( time1-time2) / 86400000);
            for (i = 1900; i < 2100 && offset > 0; i++) {
                temp = lYearDays(i).intValue();
                offset -= temp;
            }

            if (offset < 0) {
                offset += temp;
                i--;
            }

            this.year = i;
            leap = leapMonth(i).intValue(); //闰哪个月
            this.isLeap = false;

            for (i = 1; i < 13 && offset > 0; i++) {
                //闰月
                if (leap > 0 && i == (leap + 1) && this.isLeap == false) {
                    --i;
                    this.isLeap = true;
                    temp = leapDays(this.year);
                }
                else {
                    temp = monthDays(this.year, i);
                }

                //解除闰月
                if (this.isLeap == true && i == (leap + 1)) this.isLeap = false;

                offset -= temp;
            }

            if (offset == 0 && leap > 0 && i == leap + 1)
                if (this.isLeap) {
                    this.isLeap = false;
                }
                else {
                    this.isLeap = true;
                    --i;
                }

            if (offset < 0) {
                offset += temp;
                --i;
            }

            this.month = i;
            this.day = offset + 1;
        }




    }

    public class Easter{

        public int m;
        public int d;
        public Easter(int y) throws ParseException {
            int term2 = sTerm(y, 5); //取得春分日期
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            df2.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date dayTerm2 = df2.parse("" +y + "-" +3 + "-" + term2 + " 00:00:00");//取得春分的公历日期控件(春分一定出现在3月)
            Lunar lDayTerm2 = new Lunar(dayTerm2); //取得取得春分农历
            int lMlen=0;
            if (lDayTerm2.day < 15) //取得下个月圆的相差天数
                lMlen = 15 - lDayTerm2.day;
            else
                lMlen = (lDayTerm2.isLeap ? leapDays(y) : monthDays(y, lDayTerm2.month)) - lDayTerm2.day + 15;

            //一天等于 1000*60*60*24 = 86400000 毫秒
            Date l15 = new Date(dayTerm2.getTime() + 86400000 * lMlen); //求出第一次月圆为公历几日
            Date dayEaster = new Date(l15.getTime() + 86400000 * ( 7 - l15.getDay() )); //求出下个周日

            this.m = dayEaster.getMonth();
            this.d = dayEaster.getDate();
        }
    }
    public static  class Element{
        public  int sYear;
        public int  sMonth;
        public int sDay;
        public char  week;
        public int lYear;
        public int  lMonth;
        public String lMonthChinese;
        public String lDayChinese;
        public int  lDay;
        public boolean  isLeap;
        public String  cYear;
        public String cMonth;
        public String cDay;
        public String color;
        public boolean isToday=false;
        public String lunarFestival;
        public String solarFestival;
        public String solarTerms;
        public String sgz5;
        public char sgz3;
        public String cAnimal;

        public Element(int sYear,int  sMonth, int sDay,char  week,int lYear,int  lMonth,int  lDay,boolean  isLeap,String  cYear, String cMonth, String cDay, String cAnimal) {

            this.isToday = false;
            //瓣句
            this.sYear = sYear;   //公元年4位数字
            this.sMonth = sMonth;  //公元月数字
            this.sDay = sDay;    //公元日数字
            this.week = week;    //星期, 1个中文
            //农历
            this.lYear = lYear;   //公元年4位数字
            this.lMonth = lMonth;  //农历月数字
            this.lDay = lDay;    //农历日数字
            this.isLeap = isLeap;  //是否为农历闰月?
            //中文
            this.lMonthChinese=monthChinese[lMonth-1];
            this.lDayChinese=dayChinese[lDay-1];
            //八字
            this.cYear = cYear;   //年柱, 2个中文
            this.cMonth = cMonth;  //月柱, 2个中文
            this.cDay = cDay;    //日柱, 2个中文

            this.color = "";

            this.lunarFestival = ""; //农历节日
            this.solarFestival = ""; //公历节日
            this.solarTerms = ""; //节气
            this.cAnimal = cAnimal;
        }

        public String getSgz5() {
            return sgz5;
        }

        public void setSgz5(String sgz5) {
            this.sgz5 = sgz5;
        }

        public char getSgz3() {
            return sgz3;
        }

        public void setSgz3(char sgz3) {
            this.sgz3 = sgz3;
        }

        public int getsYear() {
            return sYear;
        }

        public void setsYear(int sYear) {
            this.sYear = sYear;
        }

        public int getsMonth() {
            return sMonth;
        }

        public void setsMonth(int sMonth) {
            this.sMonth = sMonth;
        }

        public int getsDay() {
            return sDay;
        }

        public void setsDay(int sDay) {
            this.sDay = sDay;
        }

        public char getWeek() {
            return week;
        }

        public void setWeek(char week) {
            this.week = week;
        }

        public int getlYear() {
            return lYear;
        }

        public void setlYear(int lYear) {
            this.lYear = lYear;
        }

        public int getlMonth() {
            return lMonth;
        }

        public void setlMonth(int lMonth) {
            this.lMonth = lMonth;
        }

        public int getlDay() {
            return lDay;
        }

        public void setlDay(int lDay) {
            this.lDay = lDay;
        }

        public boolean isLeap() {
            return isLeap;
        }

        public void setLeap(boolean leap) {
            isLeap = leap;
        }

        public String getcYear() {
            return cYear;
        }

        public void setcYear(String cYear) {
            this.cYear = cYear;
        }

        public String getcMonth() {
            return cMonth;
        }

        public void setcMonth(String cMonth) {
            this.cMonth = cMonth;
        }

        public String getcDay() {
            return cDay;
        }

        public void setcDay(String cDay) {
            this.cDay = cDay;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public boolean isToday() {
            return isToday;
        }

        public void setToday(boolean today) {
            isToday = today;
        }

        public String getLunarFestival() {
            return lunarFestival;
        }

        public void setLunarFestival(String lunarFestival) {
            this.lunarFestival = lunarFestival;
        }

        public String getSolarFestival() {
            return solarFestival;
        }

        public void setSolarFestival(String solarFestival) {
            this.solarFestival = solarFestival;
        }

        public String getSolarTerms() {
            return solarTerms;
        }

        public void setSolarTerms(String solarTerms) {
            this.solarTerms = solarTerms;
        }

        public String getlMonthChinese() {
            return lMonthChinese;
        }

        public void setlMonthChinese(String lMonthChinese) {
            this.lMonthChinese = lMonthChinese;
        }

        public String getlDayChinese() {
            return lDayChinese;
        }

        public void setlDayChinese(String lDayChinese) {
            this.lDayChinese = lDayChinese;
        }

        public String getcAnimal() {
            return cAnimal;
        }

        public void setcAnimal(String cAnimal) {
            this.cAnimal = cAnimal;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "sYear=" + sYear +
                    ", sMonth=" + sMonth +
                    ", sDay=" + sDay +
                    ", week=" + week +
                    ", lYear=" + lYear +
                    ", lMonth=" + lMonth +
                    ", lMonthChinese='" + lMonthChinese + '\'' +
                    ", lDayChinese='" + lDayChinese + '\'' +
                    ", lDay=" + lDay +
                    ", isLeap=" + isLeap +
                    ", cYear='" + cYear + '\'' +
                    ", cMonth='" + cMonth + '\'' +
                    ", cDay='" + cDay + '\'' +
                    ", color='" + color + '\'' +
                    ", isToday=" + isToday +
                    ", lunarFestival='" + lunarFestival + '\'' +
                    ", solarFestival='" + solarFestival + '\'' +
                    ", solarTerms='" + solarTerms + '\'' +
                    ", sgz5='" + sgz5 + '\'' +
                    ", sgz3=" + sgz3 +
                    ", cAnimal=" + cAnimal +
                    '}';
        }
    }


}