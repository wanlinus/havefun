package cn.wanlinus;

import java.util.Scanner;

/**
 * 打印数字灯
 *
 * @author wanli
 */
public class NumberDisplay {
    private static final char[] TOP = {'2', '3', '5', '6', '7', '8', '9', '0'};
    private static final char[] TOP_LEFT = {'5', '6'};
    private static final char[] TOP_LEFT_RIGHT = {'4', '8', '9', '0'};
    private static final char[] TOP_RIGHT = {'1', '2', '3', '7'};
    private static final char[] MIDDLE = {'2', '3', '4', '5', '6', '8', '9'};
    private static final char[] BOTTOM_LEFT = {'2'};
    private static final char[] BOTTOM_LEFT_RIGHT = {'6', '8', '0'};
    private static final char[] BOTTOM_RIGHT = {'1', '3', '4', '5', '7', '9'};
    private static final char[] BOTTOM = {'2', '3', '5', '6', '8', '9', '0'};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        draw(str);
    }

    private static void draw(String str) {
        char[] chars = str.toCharArray();
        for (char c : chars) {
            if (c < '0' || c > '9') {
                throw new RuntimeException("现在只能打印数字,请检查是否输入正确");
            }
        }

        across(chars, TOP);
        leftRight(chars, TOP_LEFT, TOP_LEFT_RIGHT, TOP_RIGHT);
        across(chars, MIDDLE);
        leftRight(chars, BOTTOM_LEFT, BOTTOM_LEFT_RIGHT, BOTTOM_RIGHT);
        across(chars, BOTTOM);
    }

    private static boolean exist(int compare, char[] array) {
        for (int i : array) {
            if (i == compare) {
                return true;
            }
        }
        return false;
    }

    /**
     * 画竖线
     *
     * @param chars     需要画的字符串数组
     * @param left      画左边
     * @param leftRight 画两边
     * @param right     画右边
     */
    private static void leftRight(char[] chars, char[] left, char[] leftRight, char[] right) {
        for (char c : chars) {
            if (exist(c, left)) {
                System.out.print("|   ");
            } else if (exist(c, leftRight)) {
                System.out.print("| | ");
            } else if (exist(c, right)) {
                System.out.print("  | ");
            } else {
                System.out.print("    ");
            }
        }
        System.out.println();
    }

    /**
     * 画横线
     *
     * @param chars 需要画的字符串
     * @param down  规则
     */
    private static void across(char[] chars, char[] down) {
        for (char c : chars) {
            if (exist(c, down)) {
                System.out.print(" -  ");
            } else {
                System.out.print("    ");
            }
        }
        System.out.println();
    }
}
