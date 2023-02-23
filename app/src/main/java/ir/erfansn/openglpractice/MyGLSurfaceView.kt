package ir.erfansn.openglpractice

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {

    private var renderer: MyGLSurfaceRenderer = MyGLSurfaceRenderer()
    private var previousX: Float = 0f
    private var previousY: Float = 0f

    init {
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)
        setWillNotDraw(false)
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        val x: Float = event.x
        val y: Float = event.y

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {

                var dx: Float = x - previousX
                var dy: Float = y - previousY

                // reverse direction of rotation above the mid-line
                if (y > height / 2) {
                    dx *= -1
                }

                // reverse direction of rotation to left of the mid-line
                if (x < width / 2) {
                    dy *= -1
                }

                renderer.rotationAngle += (dx + dy) * TOUCH_SCALE_FACTOR
                requestRender()
            }
            MotionEvent.ACTION_DOWN -> {
                // Render the view only when there is a change in the drawing data.
                // To allow the triangle to rotate automatically, this line is commented out:
                renderMode = RENDERMODE_WHEN_DIRTY
                renderer.enableAutoRotation = false
            }
            MotionEvent.ACTION_UP -> {
                renderMode = RENDERMODE_CONTINUOUSLY
                renderer.enableAutoRotation = true
            }
        }

        previousX = x
        previousY = y
        return true
    }

    companion object {
        private const val TOUCH_SCALE_FACTOR: Float = 180.0f / 320f
    }
}
