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
			<LINK href="/STYLE/history.css" rel="stylesheet" media="all" type="text/css"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"/>
			<SCRIPT src="/SCRIPT/gcse.js"/>
			<SCRIPT src="/SCRIPT/history.js"/>
			<TITLE>旅图中国跨境电商 &#187; 会员个人订单历程</TITLE>
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
								<ASIDE>
									<A id="aside1">会员管理</A>
									<A id="aside2" href="/me.asp">基本资料</A>
									<A id="aside3" href="/history.asp">订单查询</A>
									<A href="/shadow.asp">变更密码</A>
								</ASIDE>
								<DIV>
									<H1 id="asideName">会员个人订单历程</H1>
									<P>&#160;</P>
									<FORM id="pagination" action="{pagination/@action}">
										<TABLE id="list">
											<THEAD>
												<TR>
													<TH>订单编号</TH>
													<TH>日期</TH>
													<TH>店家名称</TH>
													<TH>总价</TH>
												</TR>
											</THEAD>
											<TFOOT>
												<TR>
													<TD colspan="4">
														<DIV class="fR">
															<A class="button fa fa-fast-backward paginate" title="第一页" tabindex="{pagination/@first}">&#160;</A>
															<A class="button fa fa-backward paginate" title="上一页" tabindex="{pagination/@previous}">&#160;</A>
															<SPAN style="margin:0 3px">
																<LABEL>
																	<SPAN>每页</SPAN>
																	<INPUT class="numeric" maxlength="2" name="s" type="text" value="{pagination/@size}"/>
																	<SPAN>笔</SPAN>
																</LABEL>
																<SPAN>：</SPAN>
																<LABEL>
																	<SPAN>第</SPAN>
																	<SELECT name="p">
																		<xsl:apply-templates select="*"/>
																	</SELECT>
																	<SPAN>&#47;</SPAN>
																	<SPAN>
																		<xsl:value-of select="pagination/@totalPages"/>
																	</SPAN>
																	<SPAN>页</SPAN>
																</LABEL>
																<SPAN>&#40;共</SPAN>
																<SPAN>
																	<xsl:value-of select="pagination/@totalElements"/>
																</SPAN>
																<SPAN>笔&#41;</SPAN>
															</SPAN>
															<A class="button fa fa-forward paginate" title="下一页" tabindex="{pagination/@next}">&#160;</A>
															<A class="button fa fa-fast-forward paginate" title="最后页" tabindex="{pagination/@last}">&#160;</A>
														</DIV>
													</TD>
												</TR>
											</TFOOT>
											<TBODY>
												<xsl:for-each select="list/row">
													<TR class="packet">
														<TD class="monospace textAlignCenter">
															<A class="merchantTradeNo" tabindex="{orderNo}">
																<xsl:value-of select="orderNo"/>
															</A>
														</TD>
														<TD class="monospace textAlignCenter">
															<xsl:value-of select="timestamp"/>
														</TD>
														<TD>
															<xsl:value-of select="boothName"/>
														</TD>
														<TD class="monospace textAlignRite">
															<SPAN>&#36;</SPAN>
															<xsl:value-of select="format-number(total,'###,###')"/>
														</TD>
													</TR>
													<TR class="cart" style="display:none">
														<TD colspan="4">
															<TABLE class="cart">
																<THEAD>
																	<TR>
																		<TH>商品名称</TH>
																		<TH>规格</TH>
																		<TH>单价</TH>
																		<TH>数量</TH>
																		<TH>小计</TH>
																	</TR>
																</THEAD>
																<TBODY>
																	<xsl:for-each select="carts/*">
																		<TR>
																			<TD class="textAlignLeft">
																				<xsl:value-of select="merchandiseName"/>
																			</TD>
																			<TD class="textAlignLeft">
																				<xsl:value-of select="specification"/>
																			</TD>
																			<TD class="monospace">
																				<SPAN>&#36;</SPAN>
																				<xsl:value-of select="format-number(price,'###,###')"/>
																			</TD>
																			<TD class="monospace textAlignCenter">
																				<xsl:value-of select="quantity"/>
																			</TD>
																			<TD class="monospace textAlignRite">
																				<SPAN>&#36;</SPAN>
																				<xsl:value-of select="format-number(subTotal,'###,###')"/>
																			</TD>
																		</TR>
																	</xsl:for-each>
																</TBODY>
															</TABLE>
														</TD>
													</TR>
												</xsl:for-each>
											</TBODY>
										</TABLE>
									</FORM>
								</DIV>
							</DIV>
							<xsl:call-template name="footer"/>
						</NAV>
					</DIV>
				</DIV>
			</DIV>
		</BODY>
	</xsl:template>

</xsl:stylesheet>