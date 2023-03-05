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
			<LINK href="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="/STYLE/font-awesome.min.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="/STYLE/common.css" rel="stylesheet" media="all" type="text/css"/>
			<LINK href="/STYLE/signUp.css" rel="stylesheet" media="all" type="text/css"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"/>
			<SCRIPT src="//ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/i18n/jquery-ui-i18n.min.js"/>
			<SCRIPT src="/SCRIPT/gcse.js"/>
			<SCRIPT src="/SCRIPT/signUp.js"/>
			<TITLE>旅图中国跨境电商 &#187; 忘记并重设会员密码</TITLE>
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
							<DIV>
								<FORM id="signUp" action="/forgotAndResetPassword.asp" method="POST">
									<BR/>
									<DIV>旅图中国跨境电商 &#187; 忘记并重设会员密码</DIV>
									<BR/>
									<TABLE class="form">
										<CAPTION>
											<xsl:value-of select="form/@error"/>
										</CAPTION>
										<TBODY>
											<TR>
												<TH class="must">
													<LABEL for="email">帐号</LABEL>
												</TH>
												<TD>
													<INPUT class="monospace" id="email" maxlength="256" name="email" placeholder="电子邮件" required="" type="text" value="{form/email}"/>
												</TD>
											</TR>
											<TR>
												<TH class="must">
													<LABEL for="birth">生日</LABEL>
												</TH>
												<TD>
													<INPUT class="dP monospace" id="birth" maxlength="10" name="birth" readonly="" required="" type="text" value="{form/birth}"/>
												</TD>
											</TR>
											<TR>
												<TH class="must">性别</TH>
												<TD>
													<LABEL>
														<INPUT name="gender" required="" type="radio" value="false">
															<xsl:if test="form/gender='false'">
																<xsl:attribute name="checked"/>
															</xsl:if>
														</INPUT>
														<SPAN>女性</SPAN>
													</LABEL>
													<SPAN>、</SPAN>
													<LABEL>
														<INPUT name="gender" required="" type="radio" value="true">
															<xsl:if test="form/gender='true'">
																<xsl:attribute name="checked"/>
															</xsl:if>
														</INPUT>
														<SPAN>男性</SPAN>
													</LABEL>
												</TD>
											</TR>
											<TR>
												<TH class="must">
													<LABEL for="shadow">新密码</LABEL>
												</TH>
												<TD>
													<INPUT class="monospace" id="shadow" name="shadow" placeholder="八码以上英数混杂" required="" type="password"/>
												</TD>
											</TR>
											<TR>
												<TD colspan="2">
													<INPUT class="fL" style="background-color:#000" type="reset" value="重新填写"/>
													<INPUT class="fR" style="background-color:#F39019" type="submit" value="确认送出"/>
												</TD>
											</TR>
										</TBODY>
									</TABLE>
									<BR/>
								</FORM>
							</DIV>
							<xsl:call-template name="footer"/>
						</NAV>
					</DIV>
				</DIV>
			</DIV>
		</BODY>
	</xsl:template>

</xsl:stylesheet>