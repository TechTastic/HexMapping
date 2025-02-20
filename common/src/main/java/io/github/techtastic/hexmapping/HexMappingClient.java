package io.github.techtastic.hexmapping;

import com.samsthenerd.inline.InlineClient;
import com.samsthenerd.inline.api.InlineAPI;
import com.samsthenerd.inline.api.client.InlineClientAPI;
import com.samsthenerd.inline.api.data.SpriteInlineData;
import com.samsthenerd.inline.api.matching.InlineMatch;
import com.samsthenerd.inline.api.matching.MatcherInfo;
import com.samsthenerd.inline.api.matching.RegexMatcher;
import com.samsthenerd.inline.utils.TextureSprite;

import static io.github.techtastic.hexmapping.HexMapping.id;

/**
 * Common client loading entrypoint.
 */
public class HexMappingClient {
    public static void init() {
        InlineClientAPI.INSTANCE.addMatcher(new RegexMatcher.Simple(
                "circle_marker",
                id("circle_marker"),
                (text) -> new InlineMatch.DataMatch(new SpriteInlineData(new TextureSprite(id("textures/inline/markers/circle.png")))),
                MatcherInfo.fromId(id("circle_marker"))
        ));
        InlineClientAPI.INSTANCE.addMatcher(new RegexMatcher.Simple(
                "rectangle_marker",
                id("rectangle_marker"),
                (text) -> new InlineMatch.DataMatch(new SpriteInlineData(new TextureSprite(id("textures/inline/markers/rectangle.png")))),
                MatcherInfo.fromId(id("rectangle_marker"))
        ));
        InlineClientAPI.INSTANCE.addMatcher(new RegexMatcher.Simple(
                "polygon_marker",
                id("polygon_marker"),
                (text) -> new InlineMatch.DataMatch(new SpriteInlineData(new TextureSprite(id("textures/inline/markers/polygon.png")))),
                MatcherInfo.fromId(id("polygon_marker"))
        ));
        InlineClientAPI.INSTANCE.addMatcher(new RegexMatcher.Simple(
                "polyline_marker",
                id("polyline_marker"),
                (text) -> new InlineMatch.DataMatch(new SpriteInlineData(new TextureSprite(id("textures/inline/markers/polyline.png")))),
                MatcherInfo.fromId(id("polyline_marker"))
        ));
        InlineClientAPI.INSTANCE.addMatcher(new RegexMatcher.Simple(
                "icon_marker",
                id("icon_marker"),
                (text) -> new InlineMatch.DataMatch(new SpriteInlineData(new TextureSprite(id("textures/inline/markers/icon.png")))),
                MatcherInfo.fromId(id("icon_marker"))
        ));
    }
}
