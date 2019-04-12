# ProportionsBarLibrary

An easy way to visualize in proportions any of your data.

![text](assets/cropped_examples.png)

## Latest Version

## How to use

### Configuring your project dependencies
```groovy
dependencies {
    ...
    compile 'com.github:ProportionsBarLibrary:1.0'
}
```

### Creating the view
```java
ProportionsBar proportionsBar = new ProportionsBar(context);
        proportionsBar.setId(R.id.proportions_bar_1);
```

### Adding the view
```java
LinearLayout container = findViewById(R.id.container_pb);
        container.addView(proportionsBar);
```

### Addind single value or list of values
```java
ProportionsBar proportionsBar = new ProportionsBar(context);
        proportionsBar.setId(R.id.proportion_bar_1);
        proportionsBar.addValue(37)
                      .addValues(11, 30, 24, 12, 41, 27);
```

### Addind single color or list of colors
*Where are can be less colors than number of values
```java
        proportionsBar.addIntColor(R.color.example0)
                      .addIntColors(getResources().getColor(R.color.example1),
                             getResources().getColor(R.color.example2),
                             getResources().getColor(R.color.example3));
```

### Styling the view
Show/don't show gaps between the view's segments (default value = true)
```java
        proportionsBar.showGaps(false);
```
Change the gaps' size (default is 1.0 in percent of view's width)
```java
        proportionsBar.gapSize(0.8);
```
Change the gaps' color (default is white )
```java
        proportionsBar.gapColor(Color.BLACK)
        or
        proportionsBar.gapColor(R.color.black);
```
Show rounded edges (default value = false)
```java
        proportionsBar.showRoundEdges(true);
```
Change curve of the round edges (default value is 1.4)
```java
        proportionsBar.curveOfEdges(3.5);
```
