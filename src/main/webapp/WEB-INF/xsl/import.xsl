<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
		method="html"
		encoding="UTF-8"
		omit-xml-declaration="yes"
		indent="no"
		media-type="text/html"
	/>

	<!--下拉式選單群組-->
	<xsl:template match="optgroup">
		<OPTGROUP label="{@label}">
			<xsl:apply-templates/>
		</OPTGROUP>
	</xsl:template>

	<!--下拉式選單選項-->
	<xsl:template match="option">
		<OPTION value="{@value}">
			<xsl:if test="@selected">
				<xsl:attribute name="selected"/>
			</xsl:if>
			<xsl:value-of select="." disable-output-escaping="yes"/>
		</OPTION>
	</xsl:template>

	<!--前臺右上角-->
	<xsl:template name="header">
		<A id="home" href="/">
			<IMG alt="logo" src="/IMG/logo.png"/>
		</A>
		<UL class="cF">
			<xsl:if test="not(@remoteUser)">
				<LI>
					<A>
						<xsl:attribute name="href">
							<xsl:choose>
								<xsl:when test="@me">/logOut.asp</xsl:when>
								<xsl:otherwise>/logIn.asp</xsl:otherwise>
							</xsl:choose>
						</xsl:attribute>
						<xsl:choose>
							<xsl:when test="@me">登出</xsl:when>
							<xsl:otherwise>登入</xsl:otherwise>
						</xsl:choose>
					</A>
				</LI>
				<LI>
					<B>&#124;</B>
				</LI>
				<LI>
					<A>
						<xsl:attribute name="href">
							<xsl:choose>
								<xsl:when test="@me">/me.asp</xsl:when>
								<xsl:otherwise>/signUp.asp</xsl:otherwise>
							</xsl:choose>
						</xsl:attribute>
						<xsl:choose>
							<xsl:when test="@me">我的帐号</xsl:when>
							<xsl:otherwise>加入会员</xsl:otherwise>
						</xsl:choose>
					</A>
				</LI>
				<LI>
					<B>&#124;</B>
				</LI>
				<LI>
					<A href="/cart/">
						<SPAN>我的购物车</SPAN>
						<SPAN class="fa fa-shopping-cart fa-lg" id="shoppingCart">&#160;</SPAN>
						<xsl:if test="@cart">
							<SUP style="border-radius:8px;display:inline-block;width:16px;height:16px;color:#FFF;background-color:#08C;text-align:center">
								<xsl:value-of select="@cart"/>
							</SUP>
						</xsl:if>
					</A>
				</LI>
			</xsl:if>
		</UL>
		<DIV id="gcse">
			<xsl:element name="gcse:search" namespace="">
				<xsl:attribute name="linktarget">_self</xsl:attribute>
				<xsl:attribute name="enableautocomplete">true</xsl:attribute>
			</xsl:element>
		</DIV>
	</xsl:template>
	
	<!--前臺導覽工具列-->
	<xsl:template name="navigator">
		<DIV id="nav">
			<TABLE>
				<TR>
					<TD>
						<A>
							<xsl:choose>
								<xsl:when test="@requestURI='/'">
									<xsl:attribute name="class">current</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="href">/</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<SPAN>首页</SPAN>
						</A>
					</TD>
					<TD>
						<B>&#124;</B>
					</TD>
					<TD>
						<A>
							<xsl:choose>
								<xsl:when test="@requestURI='/announcements.asp'">
									<xsl:attribute name="class">current</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="href">/announcements.asp</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<SPAN>最新消息</SPAN>
						</A>
					</TD>
					<TD>
						<B>&#124;</B>
					</TD>
					<TD>
						<A>
							<xsl:choose>
								<xsl:when test="@requestURI='/register.asp'">
									<xsl:attribute name="class">current</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="href">/register.asp</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<SPAN>线上招商</SPAN>
						</A>
					</TD>
					<TD>
						<B>&#124;</B>
					</TD>
					<TD>
						<A>
							<xsl:choose>
								<xsl:when test="@requestURI='/cPanel/'">
									<xsl:attribute name="class">current</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="href">/cPanel/</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<SPAN>店家登入</SPAN>
						</A>
					</TD>
					<TD>
						<B>&#124;</B>
					</TD>
					<TD>
						<A>
							<xsl:choose>
								<xsl:when test="@requestURI='/about.htm'">
									<xsl:attribute name="class">current</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="href">/about.htm</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<SPAN>关于我们</SPAN>
						</A>
					</TD>
					<TD>
						<B>&#124;</B>
					</TD>
					<!--
					<TD>
						<A>
							<xsl:choose>
								<xsl:when test="@requestURI='/mofo/'">
									<xsl:attribute name="class">current</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="href">/mofo/</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<SPAN>店家分类</SPAN>
						</A>
					</TD>
					<TD>
						<B>&#124;</B>
					</TD>
					-->
					<TD>
						<A>
							<xsl:choose>
								<xsl:when test="@requestURI='/feedback.htm'">
									<xsl:attribute name="class">current</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="href">/feedback.htm</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<SPAN>意见反应</SPAN>
						</A>
					</TD>
					<TD>
						<B>&#124;</B>
					</TD>
					<TD>
						<A>
							<xsl:choose>
								<xsl:when test="@requestURI='/faq.htm'">
									<xsl:attribute name="class">current</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="href">/faq.htm</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<SPAN>常见问答</SPAN>
						</A>
					</TD>
					<TD>
						<B>&#124;</B>
					</TD>
					<TD>
						<A href="http://tour-map.net/" target="_blank">旅图观止</A>
					</TD>
				</TR>
			</TABLE>
		</DIV>
	</xsl:template>

	<!--前臺底部-->
	<xsl:template name="footer">
		<DIV id="booths">
			<UL>
				<xsl:for-each select="booths/*">
					<LI title="{name}">
						<A href="/store/{id}/">
							<IMG alt="{name}" src="/logo/{id}.png"/>
						</A>
					</LI>
				</xsl:for-each>
			</UL>
		</DIV>
		<FOOTER>
			<DIV>
				<TABLE>
					<TR>
						<TD>
							<UL>
								<LI>
									<H3>旅图观止国际行销</H3>
								</LI>
								<LI>
									<ADDRESS>service@tour-map.net</ADDRESS>
								</LI>
								<LI>
									<ADDRESS>电话：+886-37-351251</ADDRESS>
								</LI>
								<LI>
									<ADDRESS>传真：+886-37-353127</ADDRESS>
								</LI>
								<LI>
									<ADDRESS>苗栗市恭敬里联大1号<BR/>(产研创新暨推广大楼5楼)</ADDRESS>
								</LI>
							</UL>
						</TD>
						<TD>
							<UL>
								<LI>
									<H3>购物须知</H3>
								</LI>
								<LI>
									<A href="/privacy.htm">个人隐私权</A>
								</LI>
								<LI>
									<A href="/policy.htm">退换货条款</A>
								</LI>
								<LI>
									<A href="/statement.htm">购物权利声明</A>
								</LI>
							</UL>
						</TD>
						<TD>
							<UL>
								<LI>
									<H3>欢迎店家加入</H3>
								</LI>
								<LI>
									<A href="/register.asp">加入旅图中国跨境电商</A>
								</LI>
								<LI>
									<A href="http://tour-map.net/" target="_blank">旅图观止</A>
								</LI>
								<LI>
									<A href="javascript:void(0);">Powered by 亚诺资讯系统</A>
								</LI>
								<!--
								<LI>
									<A href="/mofo/">店家分类</A>
								</LI>
								-->
							</UL>
						</TD>
						<TH>
							<A style="color:#3664A2" href="https://www.facebook.com/T0965582823" target="_blank">
								<IMG alt="QQ" src="/IMG/facebook.png"/>
							</A>
							<SPAN>&#160;</SPAN>
							<A style="color:#3664A2" href="javascript:void(0);">
								<IMG alt="QQ" src="/IMG/QQ.png"/>
							</A>
							<SPAN>&#160;</SPAN>
							<A style="color:#DC4E41" href="javascript:void(0);">
								<IMG alt="WeChat" src="/IMG/WeChat.png"/>
							</A>
						</TH>
					</TR>
				</TABLE>
			</DIV>
		</FOOTER>
		<DIV>
			<P class="textAlignCenter" style="cursor:default">网站所有权 &#169; 2016 旅图中国跨境电商</P>
		</DIV>
	</xsl:template>

</xsl:stylesheet>