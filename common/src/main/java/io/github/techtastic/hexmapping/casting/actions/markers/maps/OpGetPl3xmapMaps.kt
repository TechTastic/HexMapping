package io.github.techtastic.hexmapping.casting.actions.markers.maps

import io.github.techtastic.hexmapping.integration.pl3xmap.Pl3xMapIntegration

class OpGetPl3xmapMaps: OpGetMaps(Pl3xMapIntegration::getMapFromLevel) {
}