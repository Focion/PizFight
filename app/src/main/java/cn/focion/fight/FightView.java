package cn.focion.fight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.mf.fire.R;

/**
 * 战力图
 *
 * @author Pirate
 * @version 1.0.0
 * @since 2016.12.19
 */
public class FightView extends View {
    
    private Context mContext;
    
    // 宽度
    private int width;
    
    // 高度
    private int height;
    
    // 边数
    private int edgeCount;
    
    // 1级战力半径
    private float innerRadius;
    
    // 战力等级
    private int levelCount;
    
    // 角度
    private float angle;
    
    // 线画笔
    private Paint mPaint;
    
    // 边线颜色
    private int edgeColor;
    
    // 文字画笔
    private Paint mTextPaint;
    
    // 文字大小
    private float textSize;
    
    // 文字颜色
    private int textColor;
    
    // 文字与图像的距离
    private float textMargin;
    
    // 结果画笔
    private Paint mLevelPaint;
    
    // 结果透明度
    private int levelAlpha;
    
    // 结果颜色
    private int levelColor;
    
    private String[] valuses;
    
    private int[] levels;
    
    public FightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize(attrs);
    }
    
    public FightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initialize(attrs);
    }
    
    /**
     * 初始化
     */
    private void initialize(AttributeSet attr) {
        int color = Color.parseColor("#000000");
        TypedArray arr = mContext.obtainStyledAttributes(attr,
                                                         R.styleable.FightView);
        edgeCount = arr.getInt(R.styleable.FightView_fv_edge, 5);
        edgeCount = edgeCount < 5 ? 5 : edgeCount > 12 ? 12 : edgeCount;
        edgeColor = arr.getColor(R.styleable.FightView_fv_edgeColor, color);
        
        angle = (float) (Math.PI * 2 / edgeCount);
        innerRadius =
                    arr.getDimension(R.styleable.FightView_fv_innerRadius, 10);
        
        textSize = arr.getDimension(R.styleable.FightView_fv_textSize, 16);
        textColor = arr.getColor(R.styleable.FightView_fv_textColor, color);
        textMargin = arr.getDimension(R.styleable.FightView_fv_textMargin, 8);
        
        levelCount = arr.getInt(R.styleable.FightView_fv_level, 1);
        levelAlpha = arr.getInteger(R.styleable.FightView_fv_levelAlpha, 50);
        levelColor = arr.getColor(R.styleable.FightView_fv_levelColor, color);
        String value = arr.getString(R.styleable.FightView_fv_values);
        valuses = TextUtils.isEmpty(value) ? new String[0] : value.split(",");
        arr.recycle();
        // 创建画笔
        if (mPaint != null)
            return;
        // 等级线画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(edgeColor);
        mPaint.setStrokeWidth(2);
        // 文字画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setAntiAlias(true);
        // 结果画笔
        mLevelPaint = new Paint();
        mLevelPaint.setColor(levelColor);
        mLevelPaint.setAlpha(levelAlpha);
        mLevelPaint.setAntiAlias(true);
        mLevelPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        drawPolygon(canvas);
        drawText(canvas);
        drawResult(canvas);
    }
    
    /**
     * 绘制等极线
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        for (int level = 1; level <= levelCount; level++) {
            // 计算当前
            float radius = innerRadius * level;
            path.reset();
            for (int edge = 0; edge < edgeCount; edge++) {
                // 计算x, y的点
                float x = (float) (Math.sin(angle * edge) * radius);
                float y = -(float) (Math.cos(angle * edge) * radius);
                if (edge == 0)
                    path.moveTo(0, -radius);
                else
                    path.lineTo(x, y);
            }
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }
    
    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas) {
        float radius = innerRadius * levelCount;
        for (int edge = 0; edge < edgeCount; edge++) {
            String value = valuses[edge];
            if (edge == 0) {
                float valueX = -value.length() * textSize / 2;
                float valueY = -radius - textMargin;
                canvas.drawText(value, valueX, valueY, mTextPaint);
            }
            else if (edgeCount % 2 == 0 && edge == edgeCount / 2) {
                float valueX = -value.length() * textSize / 2;
                float valueY = radius + textSize + textMargin / 2;
                canvas.drawText(value, valueX, valueY, mTextPaint);
            }
            else {
                // 计算x, y的点
                float x = (float) (Math.sin(angle * edge) * radius);
                float y = -(float) (Math.cos(angle * edge) * radius);
                if (y > 0 && y < 5) {
                    float valueX = radius + textMargin;
                    float valueY = textSize / 3;
                    canvas.drawText(value, valueX, valueY, mTextPaint);
                }
                else if (y < 0 && y > -5) {
                    float valueX = -radius - textSize * value.length()
                                   - textMargin / 2;
                    float valueY = textSize / 3;
                    canvas.drawText(value, valueX, valueY, mTextPaint);
                }
                else if (x > 0 && y > 0) {
                    float valueY = y + textSize / 2 + textMargin;
                    canvas.drawText(value, x, valueY, mTextPaint);
                }
                else if (x < 0 && y < 0) {
                    float valueX =
                                 x - value.length() * textSize - textMargin / 2;
                    canvas.drawText(value, valueX, y, mTextPaint);
                }
                else if (x > 0 && y < 0) {
                    float valueX = x + textMargin / 2;
                    canvas.drawText(value, valueX, y, mTextPaint);
                }
                else if (x < 0 && y > 0) {
                    float valueX = x - value.length() * textSize;
                    float valueY = y + textSize / 2 + textMargin;
                    canvas.drawText(value, valueX, valueY, mTextPaint);
                }
            }
        }
    }
    
    /**
     * 绘制结果
     */
    private void drawResult(Canvas canvas) {
        if (levels == null || levels.length == 0)
            return;
        if (levels.length != edgeCount)
            return;
        Path path = new Path();
        for (int edge = 0; edge < edgeCount; edge++) {
            int level = levels[edge];
            level = level < 0 ? 0 : level > levelCount ? levelCount : level;
            float radius = innerRadius * level;
            float x = (float) (Math.sin(angle * edge) * radius);
            float y = -(float) (Math.cos(angle * edge) * radius);
            if (edge == 0)
                path.moveTo(0, -radius);
            else
                path.lineTo(x, y);
        }
        path.close();
        canvas.drawPath(path, mLevelPaint);
    }
    
    /**
     * 设置等级
     */
    public void setLevels(int... levels) {
        this.levels = levels;
        invalidate();
    }
}
