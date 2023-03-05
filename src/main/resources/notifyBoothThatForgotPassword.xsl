<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		indent="no"
		media-type="text/html"
	/>

	<xsl:template match="document">
		<P>
			<xsl:value-of select="name"/>
			<SPAN>，您好：</SPAN>
		</P>
		<P>请至 <A href="http://kinmen.joymall.com.tw/reset.asp?code={code}" target="_blank">http://kinmen.joymall.com.tw/reset.asp?code=<xsl:value-of select="code"/></A> 重设密码，谢谢。</P>
	</xsl:template>

</xsl:stylesheet>