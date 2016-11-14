Feature: 派车(调度)

  @dispatch
  Scenario: 派车正常流程
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    When 进入"派车"页面
    Then 派车:
      | 司机姓名 | 联系电话 | 身份证号 | 车辆信息|
      | 王艳刚| 13333333333 |130804677829309865|京A12345|
    Then 我应该在"已派车"页
    Then 再次进入派车页，派车信息应该如下:
      | 司机姓名 | 联系电话 | 身份证号 | 车辆信息|
      | 王艳刚| 13333333333 |130804677829309865|京A12345|

  @dispatch
  Scenario: 修改派车信息正常流程
    Given 进入"已派车"页面
    Then 目前至少有一个"已派车"的订单
    Then 进入派车页修改派车信息:
      | 司机姓名 | 联系电话 | 身份证号 | 车辆信息|
      | 王丽丽| 13456234333 |234567893567890432|冀B12345|
    Then 我应该在"已派车"页
    When 再次进入派车页，派车信息应该如下:
      | 司机姓名 | 联系电话 | 身份证号 | 车辆信息|
      | 王丽丽| 13456234333 |234567893567890432|冀B12345|

  @dispatch
  Scenario: 待派车页联系货主按钮检验
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    Then 进行联系货主按钮功能检验

  @dispatch
  Scenario: 最多添加10个司机检验
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    When 进入"派车"页面
    Then 进行最多添加10个司机检验

  @dispatch
  Scenario: 司机姓名为空toast检验
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    When 进入"派车"页面
    Then 派车:
      | 司机姓名 | 联系电话 | 身份证号 | 车辆信息|
      |           | 13333333333 |130804677829309865|京A12345|
    Then 应有toast弹出"司机姓名不能为空"

  @dispatch
  Scenario: 联系电话为空toast检验
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    When 进入"派车"页面
    Then 派车:
      | 司机姓名 | 联系电话 | 身份证号 | 车辆信息|
      | 王丽丽   |          |130804677829309865|京A12345|
    Then 应有toast弹出"请先输入手机号码"

  @dispatch
  Scenario: 车辆信息为空toast检验
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    When 进入"派车"页面
    Then 派车:
      | 司机姓名 | 联系电话    |      身份证号       | 车辆信息|
      | 王丽丽   | 13333333333| 130804677829309865|          |
    Then 应有toast弹出"请先输入车牌号"

  @dispatch
  Scenario: 手机号码错误toast检验
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    When 进入"派车"页面
    Then 派车:
      | 司机姓名 | 联系电话    | 身份证号 | 车辆信息|
      | 王丽丽   | 1333333     |          |          |
    Then 应有toast弹出"请输入正确的手机号码"

  @dispatch
  Scenario: 身份证号错误toast检验
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    When 进入"派车"页面
    Then 派车:
      | 司机姓名 | 联系电话    | 身份证号 | 车辆信息|
      | 王丽丽   | 13333333333| 13080  | |
    Then 应有toast弹出"请输入正确的身份证号"

  @dispatch
  Scenario: 车辆信息错误toast检验
    Given 进入"待派车"页面
    Then 目前至少有一个"待派车"的订单
    When 进入"派车"页面
    Then 派车:
      | 司机姓名 | 联系电话    | 身份证号             | 车辆信息|
      | 王丽丽   | 13333333333| 130804677829309865  | abc  |
    Then 应有toast弹出"请输入正确的车牌号"

  @dispatch
  Scenario: 完成运输正常流程
    Given 进入"已派车"页面
    Then 目前至少有一个"已派车"的订单
    When 将第一个订单完成运输
    Then 我应该在"已完成"页

