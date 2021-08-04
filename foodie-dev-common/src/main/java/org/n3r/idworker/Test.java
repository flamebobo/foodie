package org.n3r.idworker;

import java.util.List;

public class Test {

	public static void main(String[] args) {
    // 给定一个有序数组nums，请删除重复出现的元素，使每个元素最多出现两次，返回删除后数组的新长度
        //
        //例如
        //nums=[1,1,1,2,3]  输出 5
        //nums=[0,0,1,1,1,1,2,3,3]  输出 7
        int[] nums = {1,2,3,4,4,4};
        System.out.println(removeDuplicates(nums));
	}

    public static int removeDuplicates(int[] nums) {
        if (null == nums || nums.length == 0) {
            return 0;
        }
        int i = 0;
        int num = 1;
        for (int j = 1; j < nums.length; j++) {

            if(nums[i] == nums[j] && num < 2){
                num++;
                i++;
                nums[i] = nums[j];
            }
            if(nums[i] != nums[j]){
                num = 1;
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }
}
