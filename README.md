# PopularRefreshLayout
Android通用可自定义下拉刷新上拉加载组件PopularRefreshLayout

功能简述
目前，小编设置两种下拉刷新效果，一种是经典默认下拉刷新效果，一种是Material风格的，如效果图所示。经过调试，PopularRefreshLayout支持ScrollView ,ListView,WebView等。
![image](https://img-blog.csdn.net/20170208205310710)

使用方式
1. gradle.build中添加引用 compile 'com.awarmisland.android:popularrefreshlayout:1.0.4'

2. 将需要下拉刷新的内容外面添加组件
```xml
 <com.awarmisland.android.popularrefreshlayout.RefreshLayout
        android:id="@+id/refresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

 <com.awarmisland.android.popularrefreshlayout.RefreshLayout />
```
3.设置下拉刷新，上拉加载监听（以默认下拉刷新效果内嵌ListView为例子）
```java
  RefreshLayout  refresh  = (RefreshLayout) findViewById(R.id.refresh);
  refresh.setAllowLoadMore(true);//设置支持上拉加载
  refresh.setRefreshListener(refreshListener);
```

[我的博客](https://blog.csdn.net/ljzdyh)
