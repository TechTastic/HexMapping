package io.github.techtastic.hexmapping.casting.actions.markers.maps

import io.github.techtastic.hexmapping.integration.bluemap.BlueMapIntegration

class OpGetBluemapMaps: OpGetMaps(BlueMapIntegration::getMapFromLevel) {
}