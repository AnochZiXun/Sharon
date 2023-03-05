<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		indent="no"
		media-type="text/html"
	/>

	<xsl:import href="/import.xsl"/>

	<xsl:template match="/">
		<xsl:text disable-output-escaping="yes">&#60;!DOCTYPE HTML&#62;</xsl:text>
		<HTML dir="ltr" lang="zh-TW">
			<xsl:apply-templates/>
		</HTML>
	</xsl:template>

	<xsl:template match="document">
		<HEAD>
			<META charset="UTF-8"/>
			<META name="viewport" content="width=device-width, initial-scale=1.0"/>
			<LINK href="/STYLE/font-awesome.min.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="/STYLE/common.css" rel="stylesheet" media="all" type="text/css"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"/>
			<SCRIPT src="/SCRIPT/gcse.js"/>
			<TITLE>旅图中国跨境电商 &#187; 交易失败</TITLE>
		</HEAD>
		<xsl:comment>
			<xsl:value-of select="system-property('xsl:version')"/>
		</xsl:comment>
		<BODY>
			<DIV>
				<DIV>
					<HEADER>
						<DIV>
							<xsl:call-template name="header"/>
						</DIV>
					</HEADER>
				</DIV>
				<DIV>
					<DIV>
						<NAV>
							<xsl:call-template name="navigator"/>
							<DIV class="cF">
								<P style="margin:36px;color:#FF5F5E;font-family:'cwTeXYen',sans-serif;font-size:36px;text-align:center">交易失败！</P>
							</DIV>
							<xsl:call-template name="footer"/>
						</NAV>
					</DIV>
				</DIV>
			</DIV>
		</BODY>
	</xsl:template>

</xsl:stylesheet>