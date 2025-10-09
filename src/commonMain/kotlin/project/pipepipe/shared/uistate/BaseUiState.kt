package project.pipepipe.shared.uistate

import project.pipepipe.database.Feed_group
import project.pipepipe.database.Subscriptions
import project.pipepipe.shared.infoitem.ChannelInfo
import project.pipepipe.shared.infoitem.CommentInfo
import project.pipepipe.shared.infoitem.DanmakuInfo
import project.pipepipe.shared.infoitem.PlaylistInfo
import project.pipepipe.shared.infoitem.SponsorBlockSegmentInfo
import project.pipepipe.shared.infoitem.StreamInfo
import project.pipepipe.shared.infoitem.SupportedServiceInfo
import project.pipepipe.shared.infoitem.helper.SearchType

interface BaseUiState

data class ErrorInfo(
    val errorId: Long,
    val errorCode: String
)

data class CommonUiState(
    val title: String?= null,
    val isLoading: Boolean = false,
    val error: ErrorInfo? = null
) : BaseUiState

data class ListUiState<I>(
    val itemList: List<I> = emptyList(),
    val nextPageUrl: String? = null,
    val firstVisibleItemIndex: Int = 0,
    val firstVisibleItemScrollOffset: Int = 0,
) : BaseUiState

data class GeneralListUiState<I>(
    val common: CommonUiState = CommonUiState(),
    val list: ListUiState<I> = ListUiState()
): BaseUiState

enum class VideoDetailPageState {
    HIDDEN,
    BOTTOM_PLAYER,
    DETAIL_PAGE,
    FULLSCREEN_PLAYER
}

data class VideoDetailEntry(
    val streamInfo: StreamInfo,
    val cachedComments: CommentUiState = CommentUiState(),
    val cachedRelatedItems: GeneralListUiState<StreamInfo> = GeneralListUiState(),
    val cachedDanmaku: List<DanmakuInfo> = emptyList(),
    val cachedSponsorBlock: SponsorBlockUiState = SponsorBlockUiState()
)


data class VideoDetailUiState(
    val common: CommonUiState = CommonUiState(),
    val streamInfoStack: List<VideoDetailEntry> = emptyList(),
    val pageState: VideoDetailPageState = VideoDetailPageState.HIDDEN,
    val danmakuEnabled: Boolean = false
) : BaseUiState {
    val currentEntry: VideoDetailEntry?
        get() = streamInfoStack.lastOrNull()

    val currentStreamInfo: StreamInfo?
        get() = currentEntry?.streamInfo

    val currentComments: CommentUiState
        get() = currentEntry?.cachedComments ?: CommentUiState()

    val currentRelatedItems: GeneralListUiState<StreamInfo>
        get() = currentEntry?.cachedRelatedItems ?: GeneralListUiState()

    val currentSponsorBlock: SponsorBlockUiState
        get() = currentEntry?.cachedSponsorBlock ?: SponsorBlockUiState()

    val currentDanmaku: List<DanmakuInfo>?
        get() = currentEntry?.cachedDanmaku

    val canNavigateBack: Boolean
        get() = streamInfoStack.size > 1
}

data class CommentUiState(
    val common: CommonUiState = CommonUiState(),
    val comments: ListUiState<CommentInfo> = ListUiState(),
    val parentComment: CommentInfo? = null,
    val replies: ListUiState<CommentInfo> = ListUiState(),
) : BaseUiState

data class SponsorBlockUiState(
    val common: CommonUiState = CommonUiState(),
    val segments: List<SponsorBlockSegmentInfo> = emptyList()
)


data class SearchSuggestion(
    val text: String,
    val isLocal: Boolean
)

data class SearchUiState(
    val common: CommonUiState = CommonUiState(),
    val list: ListUiState<StreamInfo> = ListUiState(),
    val searchQuery: String = "",
    val searchSuggestionList: List<SearchSuggestion> = emptyList(),
    val selectedService: SupportedServiceInfo? = null,
    val selectedSearchType: SearchType? = null
) : BaseUiState

data class ChannelUiState(
    val common: CommonUiState = CommonUiState(),
    val channelInfo: ChannelInfo? = null,
    val videoTab: ListUiState<StreamInfo> = ListUiState(),
    val liveTab: ListUiState<StreamInfo> = ListUiState(),
    val playlistTab: ListUiState<PlaylistInfo> = ListUiState(),
    val isSubscribed: Boolean = false
) : BaseUiState

enum class PlaylistSortMode{
    ORIGIN,
    ORIGIN_REVERSE
}
enum class PlaylistType {
    LOCAL,
    HISTORY,
    FEED,
    REMOTE
}

data class PlaylistUiState(
    val common: CommonUiState = CommonUiState(),
    val playlistInfo: PlaylistInfo? = null,
    val list: ListUiState<StreamInfo> = ListUiState(),
    val sortMode: PlaylistSortMode = PlaylistSortMode.ORIGIN,
    val searchQuery: String = "",
    val displayItems: List<StreamInfo> = emptyList(),
    val playlistType: PlaylistType = PlaylistType.LOCAL,
    val feedLastUpdated: Long? = null,
    val isRefreshing: Boolean = false
) : BaseUiState

data class DashboardUiState(
    val common: CommonUiState = CommonUiState(),
    val feedGroups: List<Feed_group> = emptyList(),
    val historyItems: List<StreamInfo> = emptyList(),
    val playlists: List<PlaylistInfo> = emptyList()
) : BaseUiState

data class SubscriptionsUiState(
    val common: CommonUiState = CommonUiState(),
    val feedGroups: List<Feed_group> = emptyList(),
    val subscriptions: List<Subscriptions> = emptyList(),
) : BaseUiState

data class BookmarkedPlaylistUiState(
    val common: CommonUiState = CommonUiState(),
    val playlists: List<PlaylistInfo> = emptyList()
) : BaseUiState