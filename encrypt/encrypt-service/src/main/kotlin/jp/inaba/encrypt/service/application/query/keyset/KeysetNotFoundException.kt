package jp.inaba.encrypt.service.application.query.keyset

import jp.inaba.encrypt.api.domain.KeysetId


class KeysetNotFoundException(keysetId: KeysetId) :
    Exception("keysetId:{$keysetId}を持つキーセットは存在しません")
