package com.kborid.setting.java.leetcode;

public class SumTwoNumbers {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    static class Solution {

        // 是否需要进位，0：不进位，1:进位
        private int RU = 0;

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

            if (null == l1 && null == l2) {
                if (RU > 0) {
                    RU = 0;
                    return new ListNode(1);
                }
                return null;
            }

            int retVal;
            ListNode retNode;
            if (null == l1) {
                retNode = l2;
                buildNode(retNode);
                retNode.next = addTwoNumbers(null, l2.next);
            } else if (null == l2) {
                retNode = l1;
                buildNode(retNode);
                retNode.next = addTwoNumbers(l1.next, null);
            } else {
                retVal = l1.val + l2.val;
                retNode = new ListNode(retVal);
                buildNode(retNode);
                retNode.next = addTwoNumbers(l1.next, l2.next);
            }

            return retNode;
        }

        private void buildNode(ListNode retNode) {
            retNode.val += RU;
            RU = retNode.val / 10;
            retNode.val = retNode.val % 10;
        }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);
        ListNode l2 = new ListNode(1);
        ListNode ret = new Solution().addTwoNumbers(l1, l2);
        while (null != ret) {
            System.out.println(ret.val);
            ret = ret.next;
        }
    }

}
