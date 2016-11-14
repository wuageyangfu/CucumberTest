Feature: 我的业务联系人

  @contacts
  Scenario: 新增业务联系人按钮校验
    Given 进入"我的业务联系人"页面
    Then 删除可被删除的业务联系人
    Then 通过操作验证新增业务联系人按钮的显示



  @contacts
  Scenario Outline: 增加删除我的业务联系人
    Given 进入"我的业务联系人"页面
    Then 删除可被删除的业务联系人
    Then 添加业务联系人"<姓名>""<电话>"提交
    Then 进入"我的业务联系人"页面
    Then 删除业务联系人"<姓名>""<电话>"

    Examples:
      | 姓名 | 电话 |
      | 莉莉 |13245677898|
      | 李玲 |15634566723|

  @contacts
  Scenario Outline: 业务联系人姓名或电话为空检验
    Given 进入"我的业务联系人"页面
    Then 删除可被删除的业务联系人
    Then 添加业务联系人"<姓名>""<电话>"提交
    Then 应有toast弹出"<提示>"
    Then 我应该在"我关注的业务联系人"页

    Examples:
    | 姓名 |     电话   |     提示       |
    |      |13245677898|请输入联系人姓名|
    | 李玲 |            |请输入联系人电话|

  @contacts
  Scenario Outline: 业务联系人姓名或电话重复添加检验
    Given 进入"我的业务联系人"页面
    Then 删除可被删除的业务联系人
    Then 添加业务联系人"<姓名1>""<电话1>"不提交
    Then 添加业务联系人"<姓名2>""<电话2>"提交
    Then 应有toast弹出"<提示>"
    Then 我应该在"我关注的业务联系人"页

    Examples:
      | 姓名1 |     电话1   | 姓名2 |     电话2  |     提示       |
      | 李玲 |13245677898 | 李玲 |15645322543 |联系人和联系电话不可用重复添加|
      | 李玲 |13245677898 | 张芳 |13245677898 |联系人和联系电话不可用重复添加|

  @contacts
  Scenario: 业务联系人未保存信息返回弹窗检查
    Given 进入"我的业务联系人"页面
    Then 删除可被删除的业务联系人
    When 点击新增业务联系人按钮后返回
    Then 应有弹框"尚未保存当前信息，是否离开"




