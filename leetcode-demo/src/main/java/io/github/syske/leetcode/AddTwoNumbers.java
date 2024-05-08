package io.github.syske.leetcode;

public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode result = new ListNode();
        addTwoNumbers2(result, (byte) 0, l1, l2);
        return result;
    }

    public void addTwoNumbers2(ListNode result, byte nextBase, ListNode l1, ListNode l2) {
        int val = nextBase;
        if (l1 != null) {
            val += l1.val;
        }
        if (l2 != null) {
            val += l2.val;
        }
        if (val >= 10) {
            val = val % 10;
            nextBase = 1;
        } else {
            nextBase = 0;
        }
        result.val = val;
        if (l1 != null && l2 != null) {
            // 有下级或者有进位
            if (l1.next != null || l2.next != null || nextBase != 0) {
                result.next = new ListNode();
                addTwoNumbers2(result.next, nextBase, l1.next, l2.next);
            }
        } else if (l1 != null) {
            // 有下级或者有进位
            if (l1.next != null || nextBase != 0) {
                result.next = new ListNode();
                addTwoNumbers2(result.next, nextBase, l1.next, null);
            }
        } else if (l2 != null) {
            // 有下级或者有进位
            if (l2.next != null || nextBase != 0) {
                result.next = new ListNode();
                addTwoNumbers2(result.next, nextBase, null, l2.next);
            }
        }
    }

    public static void main(String[] args) {
//        ListNode next = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))))));
//        ListNode l1 = new ListNode(9, next);
//        ListNode l2 = new ListNode(9, new ListNode(9, new ListNode(9, new ListNode(9))));
//        ListNode l1 = new ListNode(9, new ListNode(9, new ListNode(1)));
        ListNode l1 = new ListNode(5);
//        ListNode l2 = new ListNode(1);
        ListNode l2 = new ListNode(5);
        ListNode listNode = new AddTwoNumbers().addTwoNumbers(l1, l2);
        System.out.println(listNode);
    }
}
