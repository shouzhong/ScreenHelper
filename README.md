# ScreenHelper
## 说明
因为碎片化的缘故，屏幕适配在android上永远都是一个难题，从google用dp适配，到鸿阳大神的px适配，到今日头条回归dp（不同屏幕有相同dp宽度），经过一代代人的努力，使我们越来越接近终点。但是上面的那些适配或多或少都有缺陷，要不写很多的配置文件，要不适配的属性少，要不有侵入性等，对我们开发者很不友好。我这里用大家很少用的单位pt适配，不影响dp、sp的使用，首先这个单位的很少用意味着对旧的代码和第三方代码几乎零侵入性，其次可以设置设计图的尺寸使我们减少了无用功的长度转换计算，最后使用简单，对我们开发者友好。这个已经在我自己的项目上已经用了很长时间了，期间有遇到一些bug，不过都修复了，一直忙没时间现在开源。
## 效果图

<table>
    <tr>
        <td><img width="270" height="480" src="https://github.com/shouzhong/ScreenHelper/blob/master/Screenshots/1080_1920_3.jpg"/></td>
        <td><img width="270" height="480" src="https://github.com/shouzhong/ScreenHelper/blob/master/Screenshots/1080_1920_4.jpg"/></td>
        <td><img width="270" height="480" src="https://github.com/shouzhong/ScreenHelper/blob/master/Screenshots/1080_1920_5.jpg"/></td>
    </tr>
    <tr>
        <td><img width="270" height="450" src="https://github.com/shouzhong/ScreenHelper/blob/master/Screenshots/480_800_3.png"/></td>
        <td><img width="270" height="450" src="https://github.com/shouzhong/ScreenHelper/blob/master/Screenshots/480_800_4.png"/></td>
        <td><img width="270" height="450" src="https://github.com/shouzhong/ScreenHelper/blob/master/Screenshots/480_800_5.png"/></td>
    </tr>
    上边图为分辨率为1080*1920的屏幕，下边图为480*800的屏幕
</table>

## 使用
### 预览
首先，我们要建一个预览，以1080*1920为例，屏幕尺寸为30.6寸，具体计算方法为(sqrt(1920^2+1080^2))/72

![图一.png](https://github.com/shouzhong/ScreenHelper/blob/master/Screenshots/1.png)

### 依赖
```
implementation 'com.shouzhong:ScreenHelper:1.0.0'
```
### 代码
重写Activity的getResources方法，其中1080为设计稿尺寸，根据实际设计稿填写
```
@Override
public Resources getResources() {
    int o = super.getResources().getConfiguration().orientation;
    if (o == android.content.res.Configuration.ORIENTATION_LANDSCAPE)
        return ScreenHelper.adaptHeight(super.getResources(), 1080);
    else return ScreenHelper.adaptWidth(super.getResources(), 1080);
}
```
不想用了怎么办，一样重写Activity的getResources方法
```
@Override
public Resources getResources() {
    return ScreenHelper.closeAdapt(super.getResources());
}
```
结束。
## 方法说明
方法名 | 说明
------------ | -------------
adaptWidth | 长度适配
adaptHeight | 高度适配
closeAdapt | 关闭适配
pt2Px | pt转px
px2Pt | px转pt
