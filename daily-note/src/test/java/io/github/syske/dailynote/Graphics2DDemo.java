package io.github.syske.dailynote;

import io.github.syske.dailynote.util.FontUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

    /**
     * 使用java原生的Graphics2D画图
     *
     * 注意：step方法是每步执行的结果，必须按顺序执行，只有有了上一步的结果之后才能执行下一步，因为下一步的背景图是上一步的结果图（后一步依赖前一步）。
     * 本例子实现的功能:
     *  1、背景图上加图片
     *  2、画圆角
     *  3、画圆形头像
     *  4、图片上写字
     *
     * Created by 刘彦民 on 2018/5/5.
     *
     */
/**
 * @program: daily-note
 * @description:
 * @author: syske
 * @create: 2021-01-25 18:23
 */
    public class Graphics2DDemo {
        private String backgroundImgPath;
        private String contentImgPath;
        private String avatarUrl;
        private String nickname;
        private String head;
        private String title;
        private String wordCount;
        private String userCount;

        /**
         * 初始化操作图片的一些参数
         */
        public void init() {
            // 背景图
            backgroundImgPath = "D:/bg.png";
            // 内容图
            contentImgPath = "D:/sm.png";
            // 头像
            avatarUrl = "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLdjibSZBoU4biahgeXMLx85eYlSOrmppw3iagrLwT8VTwXBSPuS0ogencMsEPAIXqZ7eM8VrwPlSdpw/132";
            // 昵称
            nickname = "松鼠";
            // 昵称下方标题
            head = "坚持背诵3天，累计学习123个单词";
            // 缩略图下方标题
            title = "大学英语四级";
            // 单词数
            wordCount = "4657";
            // 参与用户数
            userCount = "30953";
        }

        /**
         * 所有步骤的集合
         */
        @Test
        public void drawImage() {
            try {
                long startTime = System.currentTimeMillis();
                init();
                // 目标输出文件
                File destFile = new File("D:/tmp/img/test/6.png");

                // 创建画布，大小与背景图片大小相同
                BufferedImage img = new BufferedImage(750, 1333, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = (Graphics2D) img.getGraphics();


                // 画头像
                BufferedImage bg = ImageIO.read(new File(backgroundImgPath));
                BufferedImage avatar = ImageIO.read(new URL(avatarUrl));

                // 画头像圆角，根据需要是否使用 BufferedImage.TYPE_INT_ARGB
                BufferedImage image = new BufferedImage(avatar.getWidth(), avatar.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, avatar.getWidth(), avatar.getHeight());
                Graphics2D g2 = image.createGraphics();
                image = g2.getDeviceConfiguration().createCompatibleImage(avatar.getWidth(), avatar.getHeight(), Transparency.TRANSLUCENT);
                g2 = image.createGraphics();
                g2.setComposite(AlphaComposite.Clear);
                g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
                g2.setClip(shape);
                // 使用 setRenderingHint 设置抗锯齿
                g2 = image.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillRoundRect(0, 0,avatar.getWidth(), avatar.getHeight(), 150, 150);
                g2.setComposite(AlphaComposite.SrcIn);
                g2.drawImage(avatar, 0, 0, avatar.getWidth(), avatar.getHeight(), null);
                g2.dispose();


                g.drawImage(bg.getScaledInstance(750, 1333, Image.SCALE_DEFAULT), 0, 0, null);
                g.drawImage(image.getScaledInstance(100, 100, Image.SCALE_DEFAULT), 42, 40, null);


                // 画昵称
                // 抗锯齿
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_BOLD_FONT, 32.0f));
                g.drawString(nickname, 163, 75);


                // 画标题
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 26.0f));
                g.drawString(head, 163, 115);


                // 画中间的白框
                BufferedImage content = ImageIO.read(new File(contentImgPath));
                g.drawImage(content.getScaledInstance(750, 1333, Image.SCALE_DEFAULT), 0, 0, null);


                // 画词典标题
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 30.0f));
                g.drawString(title, 295, 870);


                // 画单词数
                g.setColor(new Color(0xBF, 0xBF, 0xBF, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 22.0f));
                g.drawString("单词数 "+wordCount, 220, 910);


                // 画加入人数
                g.setColor(new Color(0xBF, 0xBF, 0xBF, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 22.0f));
                g.drawString("参加人数 "+userCount, 380, 910);


                // 画中间的小图（带弧度的）
                try {
                    BufferedImage bi1 = ImageIO.read(new File(backgroundImgPath));

                    // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
                    image = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());

                    g2 = image.createGraphics();
                    image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getWidth(), bi1.getHeight(), Transparency.TRANSLUCENT);
                    g2 = image.createGraphics();
                    g2.setComposite(AlphaComposite.Clear);
                    g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
                    g2.setClip(shape);
                    // 使用 setRenderingHint 设置抗锯齿
                    g2 = image.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.fillRoundRect(0, 0,bi1.getWidth(), bi1.getHeight(), 50, 50);
                    g2.setComposite(AlphaComposite.SrcIn);
                    g2.drawImage(bi1, 0, 0, bi1.getWidth(), bi1.getHeight(), null);

                    g.drawImage(image.getScaledInstance(256, 342, Image.SCALE_DEFAULT), 247, 458, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                g.dispose();
                ImageIO.write(img, "png", destFile);
                System.out.println("生成成功！");
                System.out.println("耗时: " + (System.currentTimeMillis()-startTime)/1000.0 + "s");
                System.out.println("生成文件路径: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 第一步: 背景图上添加图片
         */
        @Test
        public void step1() {
            try {
                BufferedImage img = new BufferedImage(750, 1333, BufferedImage.TYPE_INT_RGB);

                BufferedImage bg = ImageIO.read(new File(backgroundImgPath));
                BufferedImage content = ImageIO.read(new File(contentImgPath));

                // 中间内容框画到背景图上
                Graphics g = img.getGraphics();
                g.drawImage(bg.getScaledInstance(750, 1333, Image.SCALE_DEFAULT), 0, 0, null);
                g.drawImage(content.getScaledInstance(750, 1333, Image.SCALE_DEFAULT), 0, 0, null);

                g.dispose();
                ImageIO.write(img, "png", new File("D:/tmp/img/test/1.png"));
                System.out.println("生成成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 第二步: 画圆角图
         */
        @Test
        public void step2() {
            try {
                File srcImageFile = new File(backgroundImgPath);

                BufferedImage bi1 = ImageIO.read(srcImageFile);

                // 根据需要是否使用 BufferedImage.TYPE_INT_ARGB
                BufferedImage image = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_INT_ARGB);

                Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());

                Graphics2D g2 = image.createGraphics();
                image = g2.getDeviceConfiguration().createCompatibleImage(bi1.getWidth(), bi1.getHeight(), Transparency.TRANSLUCENT);
                g2 = image.createGraphics();
                g2.setComposite(AlphaComposite.Clear);
                g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
                g2.setClip(shape);
                // 使用 setRenderingHint 设置抗锯齿
                g2 = image.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillRoundRect(0, 0,bi1.getWidth(), bi1.getHeight(), 50, 50);
                g2.setComposite(AlphaComposite.SrcIn);
                g2.drawImage(bi1, 0, 0, bi1.getWidth(), bi1.getHeight(), null);
                g2.dispose();
                ImageIO.write(image, "png", new File("D:/tmp/img/test/2.png"));
                System.out.println("生成成功！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 第三步: 圆角图画到背景图上
         */
        @Test
        public void step3() {
            try {
                BufferedImage img = new BufferedImage(750, 1333, BufferedImage.TYPE_INT_RGB);

                BufferedImage bg = ImageIO.read(new File("D:/tmp/img/test/1.png"));
                BufferedImage p = ImageIO.read(new File("D:/tmp/img/test/2.png"));

                // 中间内容框画到背景图上
                Graphics g = img.getGraphics();
                g.drawImage(bg.getScaledInstance(750, 1333, Image.SCALE_DEFAULT), 0, 0, null);
                g.drawImage(p.getScaledInstance(256, 342, Image.SCALE_DEFAULT), 247, 458, null);

                g.dispose();
                ImageIO.write(img, "png", new File("D:/tmp/img/test/3.png"));
                System.out.println("生成成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 第四步: 画圆形头像加到背景图上
         */
        @Test
        public void step4() {
            try {
                BufferedImage img = new BufferedImage(750, 1333, BufferedImage.TYPE_INT_RGB);

                BufferedImage bg = ImageIO.read(new File("D:/tmp/img/test/3.png"));
                BufferedImage avatar = ImageIO.read(new URL(avatarUrl));

                // 画头像圆角，根据需要是否使用 BufferedImage.TYPE_INT_ARGB
                BufferedImage image = new BufferedImage(avatar.getWidth(), avatar.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, avatar.getWidth(), avatar.getHeight());
                Graphics2D g2 = image.createGraphics();
                image = g2.getDeviceConfiguration().createCompatibleImage(avatar.getWidth(), avatar.getHeight(), Transparency.TRANSLUCENT);
                g2 = image.createGraphics();
                g2.setComposite(AlphaComposite.Clear);
                g2.fill(new Rectangle(image.getWidth(), image.getHeight()));
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1.0f));
                g2.setClip(shape);
                // 使用 setRenderingHint 设置抗锯齿
                g2 = image.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillRoundRect(0, 0,avatar.getWidth(), avatar.getHeight(), 150, 150);
                g2.setComposite(AlphaComposite.SrcIn);
                g2.drawImage(avatar, 0, 0, avatar.getWidth(), avatar.getHeight(), null);
                g2.dispose();


                Graphics g = img.getGraphics();
                g.drawImage(bg.getScaledInstance(750, 1333, Image.SCALE_DEFAULT), 0, 0, null);
                g.drawImage(image.getScaledInstance(100, 100, Image.SCALE_DEFAULT), 42, 40, null);

                g.dispose();
                ImageIO.write(img, "png", new File("D:/tmp/img/test/4.png"));
                System.out.println("生成成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 第五步: 写文字
         */
        @Test
        public void step5() {
            try {
                BufferedImage bg = ImageIO.read(new File("D:/tmp/img/test/4.png"));

                Graphics2D g = bg.createGraphics();
                g.drawImage(bg.getScaledInstance(750, 1333, Image.SCALE_DEFAULT), 0, 0, null);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

                // 写昵称
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_BOLD_FONT, 32.0f));
                g.drawString(nickname, 163, 75);

                // 写标题
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 26.0f));
                g.drawString(head, 163, 115);

                // 写词典名称
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 30.0f));
                g.drawString(title, 295, 870);

                // 写单词数
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 22.0f));
                g.drawString("单词数 "+wordCount, 220, 910);

                // 写加入人数
                g.setColor(new Color(33, 33, 33, 128));
                g.setFont(FontUtil.getFont(FontUtil.PINGFANG_FONT, 22.0f));
                g.drawString("参加人数 "+userCount, 380, 910);

                g.dispose();


                ImageIO.write(bg, "png", new File("D:/tmp/img/test/5.png"));
                System.out.println("生成成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
