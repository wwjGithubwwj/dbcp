/**
 *
 */
package com.jusdt.jdbc;

/**
 * @author wwj
 * @version 创建时间：2019年6月22日 下午4:40:29
 */
public class FieldsRetriveCore {
	public static void main(String[] args) {

		String url = "http://bbs.local.163.com/list/localgd.html";
		System.out.println(url.length());//长度42
		String newurl = url.substring(7, 24);
		System.out.println(url.substring(0, 24));
		System.out.println("http://" + newurl);
	}
}
