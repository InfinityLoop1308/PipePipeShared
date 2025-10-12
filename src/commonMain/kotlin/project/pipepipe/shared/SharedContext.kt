package project.pipepipe.shared

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import project.pipepipe.database.AppDatabase
import project.pipepipe.shared.downloader.CookieManager
import project.pipepipe.shared.downloader.Downloader
import project.pipepipe.shared.viewmodel.BottomSheetMenuViewModel
import project.pipepipe.shared.viewmodel.VideoDetailViewModel
import org.ocpsoft.prettytime.PrettyTime
import project.pipepipe.shared.infoitem.Info
import project.pipepipe.shared.job.JobRequest
import project.pipepipe.shared.job.JobResponse
import project.pipepipe.shared.helper.SettingsManager
import project.pipepipe.shared.state.SessionManager
import project.pipepipe.shared.state.Cache4kSessionManager
import java.util.Locale

enum class PlaybackMode {
    VIDEO_AUDIO,
    AUDIO_ONLY
}

object SharedContext {
    lateinit var downloader: Downloader
    val objectMapper = ObjectMapper()
    lateinit var cookieManager: CookieManager
    lateinit var sharedVideoDetailViewModel: VideoDetailViewModel
    lateinit var database: AppDatabase
    lateinit var appLocale: Locale
    lateinit var serverRequestHandler: suspend (JobRequest) -> JobResponse<out Info, out Info>
    lateinit var settingsManager: SettingsManager
    val bottomSheetMenuViewModel = BottomSheetMenuViewModel()
    lateinit var sessionManager: SessionManager

    private val _playbackMode = MutableStateFlow(PlaybackMode.AUDIO_ONLY)
    val playbackMode: StateFlow<PlaybackMode> = _playbackMode.asStateFlow()

    fun updatePlaybackMode(mode: PlaybackMode) {
        _playbackMode.value = mode
    }

    private val _playQueueVisibility = MutableStateFlow(false)
    val playQueueVisibility: StateFlow<Boolean> = _playQueueVisibility.asStateFlow()
    fun toggleShowPlayQueueVisibility() {
        _playQueueVisibility.value = !_playQueueVisibility.value
    }

    private val _isInPipMode = MutableStateFlow(false)
    val isInPipMode: StateFlow<Boolean> = _isInPipMode.asStateFlow()
    fun enterPipmode() {
        _isInPipMode.value = true
    }
    fun exitPipMode() {
        _isInPipMode.value = false
    }

    // Image Viewer State
    data class ImageViewerState(
        val isVisible: Boolean = false,
        val urls: List<String> = emptyList(),
        val initialPage: Int = 0
    )

    private val _imageViewerState = MutableStateFlow(ImageViewerState())
    val imageViewerState: StateFlow<ImageViewerState> = _imageViewerState.asStateFlow()

    fun showImageViewer(urls: List<String>, initialPage: Int = 0) {
        _imageViewerState.value = ImageViewerState(
            isVisible = true,
            urls = urls,
            initialPage = initialPage
        )
    }

    fun hideImageViewer() {
        _imageViewerState.value = ImageViewerState()
    }
}