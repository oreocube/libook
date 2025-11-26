import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oreocube.booksearch.core.ui.component.icon.LiBookIcons

val LiBookIcons.Default.Empty: ImageVector
    get() {
        if (_emptyIcon != null) {
            return _emptyIcon!!
        }
        _emptyIcon = ImageVector.Builder(
            name = "CuteEmptyList",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // 둥근 박스
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(6f, 6f)
                lineTo(18f, 6f)
                arcTo(2f, 2f, 0f, false, true, 20f, 8f)
                lineTo(20f, 16f)
                arcTo(2f, 2f, 0f, false, true, 18f, 18f)
                lineTo(6f, 18f)
                arcTo(2f, 2f, 0f, false, true, 4f, 16f)
                lineTo(4f, 8f)
                arcTo(2f, 2f, 0f, false, true, 6f, 6f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color.Black),
                strokeAlpha = 0.6f,
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                // 첫 번째 짧은 줄
                moveTo(8f, 10f)
                lineTo(12f, 10f)

                // 두 번째 짧은 줄
                moveTo(8f, 12f)
                lineTo(14f, 12f)

                // 세 번째 짧은 줄
                moveTo(8f, 14f)
                lineTo(11f, 14f)
            }

            // 귀여운 느낌표
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 0.4f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(16.5f, 14.5f)
                arcTo(1f, 1f, 0f, true, true, 15.5f, 15.5f)
                arcTo(1f, 1f, 0f, true, true, 14.5f, 14.5f)
                arcTo(1f, 1f, 0f, true, true, 15.5f, 13.5f)
                arcTo(1f, 1f, 0f, true, true, 16.5f, 14.5f)
                close()
            }
        }.build()

        return _emptyIcon!!
    }

private var _emptyIcon: ImageVector? = null

@Composable
fun EmptyListView(
    message: String = "아직 아무것도 없어요",
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = LiBookIcons.Default.Empty,
            contentDescription = "Empty list",
            modifier = Modifier.size(120.dp),
            tint = iconTint
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyListViewPreview() {
    EmptyListView()
}

@Preview(showBackground = true)
@Composable
fun CuteEmptyListIconPreview() {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = LiBookIcons.Default.Empty,
            contentDescription = "Empty list",
            modifier = Modifier.size(48.dp)
        )

        Icon(
            imageVector = LiBookIcons.Default.Empty,
            contentDescription = "Empty list",
            modifier = Modifier.size(72.dp),
            tint = Color(0xFFFF6B9D)
        )

        Icon(
            imageVector = LiBookIcons.Default.Empty,
            contentDescription = "Empty list",
            modifier = Modifier.size(96.dp),
            tint = Color(0xFF9C88FF)
        )
    }
}
