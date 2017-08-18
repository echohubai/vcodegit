package vcode.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by hubai on 2017/8/8.
 */
public class PictureUtils {
    /**
     * @return
     * @throws IOException
     */
    public static BufferedImage getSlice(BufferedImage bi1,int x,int y) throws IOException {
        int width = bi1.getWidth();
        int height = bi1.getHeight();
        int sWidth=87;
        int sHeight=88;
        int pWidth = sHeight/4;
        //透明底的图片
        BufferedImage bi2 = new BufferedImage(x+sWidth, y+sHeight+pWidth, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = bi2.createGraphics();
        GeneralPath g5 = getShape(x,y);
        g2.setClip(g5);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        BufferedImage bi3 = new BufferedImage(sWidth, sHeight+pWidth, BufferedImage.TYPE_4BYTE_ABGR);
        Rectangle2D.Double rec = new Rectangle2D.Double(0,0,sWidth,sHeight+pWidth);
        Graphics2D g3 = bi3.createGraphics();
        g3.setClip(rec);
        g3.drawImage(bi2,-x,-y,null);

        return bi3;
    }
    public static BufferedImage getPicBg(BufferedImage bi1,int x,int y){
        int width = bi1.getWidth();
        int height = bi1.getHeight();
        int sWidth=87;
        int sHeight=88;
        int pWidth = sHeight/4;
        //透明底的图片
        BufferedImage bi2 = new BufferedImage(x+sWidth, y+sHeight+pWidth, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = bi2.createGraphics();
        GeneralPath g5 = getShape(x,y);
        int[][] mask = new int[width][height];
        for (int i = 0; i <width; i++) {
            for (int j = 0; j < height; j++) {
                //值得注意的是contains(j,i)
                mask[i][j] = g5.contains(i, j) ? 1 : 0;
            }

        }
        //改变图片的RGB值
        BufferedImage image = alphaProcess(mask,bi1);
        return image;
    }
    private static BufferedImage alphaProcess(int[][] mask, BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        System.out.println(width + " " + height);

        BufferedImage resImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = bufferedImage.getRGB(i, j);
                int r = (0xff & rgb);
                int g = (0xff & (rgb >> 8));
                int b = (0xff & (rgb >> 16));
                //根据mask设置透明度
                if (mask[i][j] == 1)
                    rgb = r + (g << 8) + (b << 16) + (100 << 24);
                else{
                    rgb = r + (g<<8) + (b<<16)+(255<<24);
                }
                resImage.setRGB(i, j, rgb);
            }
        }
        return resImage;
    }
    private static GeneralPath getShape(int x,int y){
        int sWidth=87;
        int sHeight=88;
        int pWidth = sHeight/4;
        Point p1=new Point(x,y);
        Point p2=new Point(x+sWidth,y);
        Point p3=new Point(x+sWidth,y+sHeight/2-pWidth);
        Point p4=new Point(x+sWidth,y+sHeight/2+pWidth);
        Point p5 = new Point(x+sWidth,y+sHeight);
        Point p6 = new Point(x+sWidth/2+pWidth,y+sHeight);
        Point p7 = new Point(x+sWidth/2-pWidth,y+sHeight);
        Point p8 = new Point(x,y+sHeight);
        GeneralPath g5 = new GeneralPath();
        g5.moveTo(p1.x,p1.y);
        g5.lineTo(p2.x,p2.y);
        g5.lineTo(p3.x,p3.y);
        g5.quadTo(x+sWidth-pWidth,y+sHeight/2,p4.x,p4.y);
        g5.lineTo(p5.x,p5.y);
        g5.lineTo(p6.x,p6.y);
        g5.quadTo(x+sWidth/2,y+sHeight+pWidth,p7.x,p7.y);
        g5.lineTo(p8.x,p8.y);
        g5.closePath();
        return g5;
    }
    public static void PictureReduct(String path,int randNum_X,int randNum_Y,String filename1,String filename2) {
        try {
            //读取本地图片地址
            Image src = ImageIO.read(new File(path));
            BufferedImage url = (BufferedImage) src;

            BufferedImage pieceImage = PictureUtils.getSlice(url,randNum_X,randNum_Y);
            BufferedImage picBg = PictureUtils.getPicBg(url,randNum_X,randNum_Y);


            //生成的图片位置
            String pieceImagePath= "G:\\vcode-5\\vcode\\target\\vcode\\images\\"+filename1;
            String bgImagePath= "G:\\vcode-5\\vcode\\target\\vcode\\images\\"+filename2;

            //将图片写入到本地图库中
            ImageIO.write(pieceImage, pieceImagePath.substring(pieceImagePath.lastIndexOf(".") + 1), new File(pieceImagePath));
            ImageIO.write(picBg, bgImagePath.substring(bgImagePath.lastIndexOf(".") + 1), new File(bgImagePath));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
