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
			<B>
				<xsl:value-of select="name"/>
			</B>
			<SPAN>店家，您好：</SPAN>
		</P>
		<P>贵公司在「中国跨境电商」的密码已重设；以下是新的登入资讯，请查核确认，谢谢。</P>
		<OL>
			<LI>
				<SPAN>店家帐号：</SPAN>
				<B style="font-family:'bitstream vera sans mono',monospace">
					<xsl:value-of select="login"/>
				</B>
			</LI>
			<LI>
				<SPAN>新的密码：</SPAN>
				<B style="font-family:'bitstream vera sans mono',monospace">
					<xsl:value-of select="shadow"/>
				</B>
			</LI>
			<LI>
				<SPAN>登入网址：</SPAN>
				<B style="font-family:'bitstream vera sans mono',monospace">http://kinmen.joymall.com.tw/cPanel/</B>
			</LI>
		</OL>
		<P>谢谢您的光临！</P>
	</xsl:template>

</xsl:stylesheet>