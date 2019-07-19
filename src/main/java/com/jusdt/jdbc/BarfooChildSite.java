package com.jusdt.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BarfooChildSite {

	public static final Logger logger = LoggerFactory.getLogger(BarfooChildSite.class);


	public static void main(String[] args) {
		DbcpClient dbcpClient = new DbcpClient();
		//		List<SiteicpSiteInfo> list = dbcpClient.selectSiteList();
		List<BarfoochildSiteInfo> list = dbcpClient.selectBarfooChildSiteList();
		//		logger.info("selectSiteList success");
		logger.info("selectBarfooChildSiteList success");

		//		for (int i = 0; i < list.size(); i++) {
		//			System.out.println(list.get(i));
		//			SiteicpSiteInfo SiteicpSiteInfo = list.get(i);
		//			//先读取barfoo_childsite中name和url的记录，然后写向表site_icp中host，hostname。以及httphost中去
		//			dbcpClient.insertSiteicp(SiteicpSiteInfo.getUrl(), SiteicpSiteInfo.getName(), SiteicpSiteInfo.getUrl());
		//		}

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			BarfoochildSiteInfo barfoochildSiteInfo = list.get(i);
			//先读取barfoo_childsite中name和url的记录，然后写向表site_icp中host，hostname。以及httphost中去
			dbcpClient.deleteBarfooChildsite(barfoochildSiteInfo.getName());
		}

		//		for (int i = 0; i < list.size(); i++) {
		//			System.out.println(list.get(i));
		//			SiteInfo siteInfo = list.get(i);
		//			//提取表site_icp中hostname字段中下划线之前的字符
		//			dbcpClient.updateSiteicpHostname(siteInfo.getName());
		//		}
		//
		//		for (int i = 0; i < list.size(); i++) {
		//			System.out.println(list.get(i));
		//			SiteInfo siteInfo = list.get(i);
		//			//提取表site_icp中host字段中的域名信息
		//			dbcpClient.updateSiteicpHost(siteInfo.getUrl());
		//
		//		}

		dbcpClient.close();
	}

}
