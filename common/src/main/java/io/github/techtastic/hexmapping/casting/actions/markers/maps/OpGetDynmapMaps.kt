package io.github.techtastic.hexmapping.casting.actions.markers.maps

import io.github.techtastic.hexmapping.integration.dynmap.DynMapIntegration

class OpGetDynmapMaps: OpGetMaps(DynMapIntegration::getMapFromLevel) {
}