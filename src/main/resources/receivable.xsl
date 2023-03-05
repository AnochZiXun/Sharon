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
			<xsl:value-of select="regular"/>
			<SPAN>，您好：</SPAN>
		</P>
		<P>
			<SPAN>感谢您在「中国跨境电商『</SPAN>
			<B style="color:#79AE3D">
				<xsl:value-of select="booth"/>
			</B>
			<SPAN>』」的购物；以下是您的订单资讯，谢谢。</SPAN>
		</P>
		<TABLE style="border-collapse:collapse" border="1" cellspacing="6" cellpadding="6">
			<CAPTION style="text-align:left">
				<P>
					<SPAN>交易编号：</SPAN>
					<SPAN class="monospace" title="{when}">
						<xsl:value-of select="merchantTradeNo"/>
					</SPAN>
				</P>
			</CAPTION>
			<THEAD>
				<TR>
					<TH style="text-align:center">产品名称</TH>
					<TH style="text-align:center">规格</TH>
					<TH style="text-align:center">单价</TH>
					<TH style="text-align:center">数量</TH>
					<TH style="text-align:right">小计</TH>
				</TR>
			</THEAD>
			<TFOOT>
				<TR>
					<TD style="text-align:right" colspan="4">合计</TD>
					<TD style="font-family:'bitstream vera sans mono',monospace;text-align:right">
						<xsl:value-of select="format-number(total,'###,###')"/>
						<SPAN>元</SPAN>
					</TD>
				</TR>
			</TFOOT>
			<TBODY>
				<xsl:for-each select="packet/*">
					<TR>
						<TD style="text-align:center">
							<xsl:value-of select="."/>
						</TD>
						<TD style="text-align:center">
							<xsl:value-of select="@specification"/>
						</TD>
						<TD style="text-align:center">
							<xsl:value-of select="format-number(@price,'###,###')"/>
							<SPAN>元</SPAN>
						</TD>
						<TD style="text-align:center">
							<xsl:value-of select="@quantity"/>
						</TD>
						<TD style="font-family:'bitstream vera sans mono',monospace;text-align:right">
							<xsl:value-of select="format-number(@subTotal,'###,###')"/>
							<SPAN>元</SPAN>
						</TD>
					</TR>
				</xsl:for-each>
			</TBODY>
		</TABLE>
	</xsl:template>

</xsl:stylesheet>