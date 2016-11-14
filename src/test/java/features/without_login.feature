#Feature: 安卓五阿哥车队版测试-登录界面
#
#  Scenario Outline: 登录退出测试
#    Given 用"<用户名>"和"<密码>"登录
#    Then 登录"<成功>"
#    When 退出app
#    Then 我应该在"登录"页
#
#    Examples:
#      | 用户名 | 密码 |成功|
#      | 13051278309      | yangfu12     |true|
#
#  Scenario Outline: 登录失败测试
#    Given 用"<用户名>"和"<密码>"登录
#    Then 登录"<失败>"
#    Then 我应该在"登录"页
#
#    Examples:
#      | 用户名 | 密码 |失败|
#      | 13051278309      | 123456     |false|
#
#  Scenario Outline: 注册:手机号码已注册
#    Given 进入"注册"页面
#    When 输入"<已注册手机号码>"并点击发送验证码按钮
#    Then 应有toast弹出"<提示>"
#
#    Examples:
#      | 已注册手机号码 | 提示|
#      | 13051278309|账号已存在，不能重复注册|
#
#  Scenario Outline: 免登录测试
#    Given 用"<用户名>"和"<密码>"登录
#    When 登录"<成功>"
#    Then 关闭并重新打开app
#    Then 登录"<成功>"
#    When 退出app
#    Then 我应该在登录页
#
#    Examples:
#      | 用户名 | 密码 |成功|
#      | 13051278309      | yangfu12     |true|