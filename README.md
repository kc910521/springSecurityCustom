# springSecurityCustom
spring mvc security jpa with custom login filter in JAVA CONFIG
# 解决的问题
* 1、整合springmvc security jpa，从数据库表获取角色信息和用户信息。
* 2、重写UsernamePasswordAuthenticationFilter（MyAuthenticationFilter），
进行简单的登陆界面验证信息自定义。
* 3、着手cas ing
ps:为毛spring security身份验证不通过，会抛出异常让外层filter接到进行解析，有没有其他方式？
* 4、区分ajax调用和普通调用权限不足时的处理结果。
/n 修改基于siva的基础项目，来自于：
[github](https://github.com/sivaprasadreddy/sivalabs-blog-samples-code)


