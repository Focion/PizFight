# Fight

FightView是一个战力图控件

#### Image
![avatar](https://raw.githubusercontent.com/Focion/PizRes/master/images/img_fight_view.png)

#### Version

  - 1.0

#### How to use

###### xml
```xml
        <cn.focion.fight.FightView
           android:id="@+id/fight_view"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           app:fv_edge="5"
           app:fv_edgeColor="@android:color/holo_blue_light"
           app:fv_innerRadius="12dp"
           app:fv_level="10"
           app:fv_levelAlpha="100"
           app:fv_levelColor="@color/colorAccent"
           app:fv_textColor="@color/colorPrimary"
           app:fv_textMargin="8dp"
           app:fv_textSize="14sp"
           app:fv_values="value1,value2,value3,value4,value5" />
```

###### Activity
```java
        FightView fightView = (FightView) findViewById(R.id.fight_view);
        // 设置显示等级的值
        fightView.setLevels(10, 2, 1, 9, 4);
```

#### attribute
```xml
        // 边数
        <attr name="fv_edge" format="integer" />
        // 边颜色
        <attr name="fv_edgeColor" format="color" />
        // 最小等级的半径
        <attr name="fv_innerRadius" format="dimension" />
        // 等级数
        <attr name="fv_level" format="integer" />
        // 等级绘制透明度
        <attr name="fv_levelAlpha" format="integer" />
        // 等级绘制颜色
        <attr name="fv_levelColor" format="color" />
        // 等级显示文字大小
        <attr name="fv_textSize" format="dimension" />
        // 等级显示文字颜色
        <attr name="fv_textColor" format="color" />
        // 文字对应图片距离
        <attr name="fv_textMargin" format="dimension" />
        // 显示的文字值
        <attr name="fv_values" format="string" />
```

#### License
  * [Apache Licene 2.0]


[Apache Licene 2.0]:<http://www.apache.org/licenses/LICENSE-2.0>
