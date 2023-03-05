<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		indent="no"
		media-type="text/html"
	/>

	<xsl:template match="form">
		<P>
			<xsl:value-of select="name"/>
			<SPAN>，您好：</SPAN>
		</P>
		<P>欢迎您加入「旅图中国跨境电商」店家；以下是您的注册资讯，请查核确认，谢谢。</P>
		<OL>
			<LI>
				<SPAN>注册帐号：</SPAN>
				<B style="font-family:'bitstream vera sans mono',monospace">
					<xsl:value-of select="login"/>
				</B>
			</LI>
			<LI>
				<SPAN>注册密码：</SPAN>
				<B style="font-family:'bitstream vera sans mono',monospace">
					<xsl:value-of select="shadow"/>
				</B>
			</LI>
			<LI>
				<SPAN>登入网址：</SPAN>
				<B style="font-family:'bitstream vera sans mono',monospace">http://kinmen.joymall.com.tw/cPanel/</B>
			</LI>
		</OL>
		<P>欢迎您的加入！</P>
	</xsl:template>

</xsl:stylesheet>