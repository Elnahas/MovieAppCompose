
import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import kotlin.math.abs
import kotlin.math.roundToInt

/* ---------------- Shapes ---------------- */

sealed interface ImageShape {
    data object Circle : ImageShape
    data object Oval : ImageShape
    data class Rounded(val cornerRadius: Dp = 16.dp) : ImageShape
}

private fun ImageShape.toComposeShape(): Shape = when (this) {
    is ImageShape.Circle -> CircleShape
    is  ImageShape.Oval -> OvalShape
    is ImageShape.Rounded -> RoundedCornerShape(cornerRadius)
    else -> {OvalShape}
}

private object OvalShape : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): androidx.compose.ui.graphics.Outline {
        val path = Path().apply {
            addOval(androidx.compose.ui.geometry.Rect(Offset.Zero, size))
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}

/* -------------- Public API -------------- */

data class PreviewConfig(
    val enabled: Boolean = true,
    val dragToDismiss: Boolean = true,
    val dismissThresholdPx: Float = 140f
)

/**
 * صورة شبكية ذكية قابلة لإعادة الاستخدام مبنية على Coil 3 + rememberAsyncImagePainter.
 *
 * - شِمّر أثناء التحميل أو placeholder ثابت.
 * - fallback عند غياب الرابط.
 * - error painter أو أيقونة افتراضية.
 * - أشكال (دائري/بيضاوي/مستطيل بزوايا).
 * - فتح معاينة بملء الشاشة مع Zoom + سحب رأسي للإغلاق.
 */
@Composable
fun NetworkImage(
    imageUrl: String?,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    // الحجم
    size: Dp? = null,
    width: Dp? = null,
    height: Dp? = null,
    // المظهر
    shape: ImageShape = ImageShape.Rounded(16.dp),
    border: BorderStroke? = null,
    contentScale: ContentScale = ContentScale.Crop,
    // حالات العرض
    placeholder: Painter? = null,
    errorPainter: Painter? = null,
    fallbackPainter: Painter? = null,
    showShimmer: Boolean = true,
    crossfade: Boolean = true,
    loadOriginalSize: Boolean = false,
    // تفاعل
    preview: PreviewConfig = PreviewConfig(),
    onClick: (() -> Unit)? = null,
    onLongPress: (() -> Unit)? = null,
    // callbacks
    onSuccess: (() -> Unit)? = null,
    onError: ((Throwable?) -> Unit)? = null
) {
    // تحضير الموديـفاير (حجم + قص + بوردر)
    var decorated = modifier
    decorated = when {
        size != null -> decorated.size(size)
        else -> {
            var m = decorated
            if (width != null) m = m.width(width)
            if (height != null) m = m.height(height)
            m
        }
    }
    val composeShape = shape.toComposeShape()
    decorated = decorated.clip(composeShape)
    if (border != null) decorated = decorated.border(border, composeShape)

    // لو مفيش لينك: اعرض fallback أو أيقونة افتراضية وارجع
    if (imageUrl.isNullOrBlank()) {
        Box(modifier = decorated.background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (fallbackPainter != null) {
                Image(
                    painter = fallbackPainter,
                    contentDescription = contentDescription,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Image,
                    contentDescription = contentDescription,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }

    val context = LocalContext.current
    val request = remember(imageUrl, crossfade, loadOriginalSize) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(crossfade)               // وثائق Coil: crossfade عبر ImageRequest
            .apply { if (loadOriginalSize) size(Size.ORIGINAL) }  // تعيين الحجم الأصلي عند الحاجة
            .build()
    }

    var openPreview by remember { mutableStateOf(false) }

    val painter = rememberAsyncImagePainter(
        model = request,
        onSuccess = { onSuccess?.invoke() },
        onError = { onError?.invoke(it.result.throwable) }
    )
    // في Coil 3 الحالة هي StateFlow -> لازم collectAsState
    val state by painter.state.collectAsState()

    Box(
        modifier = decorated
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .pointerInput(preview.enabled) {
                detectTapGestures(
                    onLongPress = { onLongPress?.invoke() },
                    onTap = {
                        onClick?.invoke()
                        if (preview.enabled) openPreview = true
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // الصورة
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )

        // Loading overlay
        if (state is AsyncImagePainter.State.Loading) {
            if (placeholder != null) {
                Image(
                    painter = placeholder,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            }
            if (showShimmer) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(rememberShimmerBrush()),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(strokeWidth = 2.dp) }
            }
        }

        // Error overlay
        if (state is AsyncImagePainter.State.Error) {
            if (errorPainter != null) {
                Image(
                    painter = errorPainter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = contentScale
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = "Image error",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (preview.enabled && openPreview) {
        FullscreenPreview(
            imageUrl = imageUrl,
            contentDescription = contentDescription,
            onDismiss = { openPreview = false },
            dragToDismiss = preview.dragToDismiss,
            dismissThresholdPx = preview.dismissThresholdPx
        )
    }
}

/* ---------------- Preview (zoom + drag-to-dismiss) ---------------- */

@SuppressLint("ClickableViewAccessibility")
@Composable
private fun FullscreenPreview(
    imageUrl: String,
    contentDescription: String?,
    onDismiss: () -> Unit,
    dragToDismiss: Boolean,
    dismissThresholdPx: Float
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var bgAlpha by remember { mutableFloatStateOf(1f) }

    Dialog(onDismissRequest = onDismiss) {
        // خلفية معتمدة تتأثر بالسحب
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = bgAlpha))
        ) {
            val context = LocalContext.current
            val request = remember(imageUrl) {
                ImageRequest.Builder(context).data(imageUrl).build()
            }
            val painter = rememberAsyncImagePainter(model = request)

            val state by painter.state.collectAsState()

            // المحتوى
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                    .scale(scale)
                    .pointerInput(dragToDismiss) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            val newScale = (scale * zoom).coerceIn(1f, 5f)
                            val scaleRatio = newScale / scale
                            scale = newScale
                            offset += pan * scaleRatio

                            // سحب رأسي للإغلاق (يعمل فقط عند عدم التكبير)
                            if (dragToDismiss && scale <= 1.02f) {
                                // غيّر شفافية الخلفية مع المسافة
                                val progress = (abs(offset.y) / (dismissThresholdPx * 2f)).coerceIn(0f, 1f)
                                bgAlpha = 1f - progress
                                if (abs(offset.y) > dismissThresholdPx) {
                                    onDismiss()
                                }
                            } else {
                                bgAlpha = 1f
                            }
                        }
                    }
                    .pointerInput(Unit) {
                        // Double tap zoom
                        detectTapGestures(
                            onDoubleTap = {
                                if (scale > 1.01f) {
                                    scale = 1f; offset = Offset.Zero
                                } else {
                                    scale = 2f
                                }
                            },
                            onTap = { if (scale <= 1.01f) onDismiss() }
                        )
                    },
                contentScale = ContentScale.Fit
            )

            // مؤشرات الحالة في المعاينة
            when (state) {
                is AsyncImagePainter.State.Loading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(color = Color.White, strokeWidth = 2.dp) }

                is AsyncImagePainter.State.Error -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { Icon(Icons.Filled.Error, null, tint = Color.Red) }

                else -> Unit
            }
        }
    }
}

/* ---------------- Shimmer ---------------- */

@Composable
private fun rememberShimmerBrush(): Brush {
    val t = rememberInfiniteTransition(label = "shimmer")
    val x by t.animateFloat(
        initialValue = 0f,
        targetValue = 1200f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "x"
    )
    val colors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
    )
    return Brush.linearGradient(
        colors = colors,
        start = Offset(x - 300f, 0f),
        end = Offset(x, 0f)
    )
}
