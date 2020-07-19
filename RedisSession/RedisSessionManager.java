package jp.co.ssd.demo.web.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * セッション操作クラス
 *
 * @author ハイマックス 大松
 *
 */
@Component
public class RedisSessionManager {
  /**
   * セッション
   */
  @Autowired
  HttpSession session;
  /**
   * セッションに値を格納する
   *
   * @param key キー
   * @param value 設定値
   */
  public void set(String key, Object value) {
    session.setAttribute(key, value);
  }

  /**
   * セッションから値を取得する
   *
   * @param key キー
   * @return 取得した値。キーが存在しない場合はnull。
   */
  public Object get(String key) {
    return session.getAttribute(key);
  }

  /**
   * セッションから指定したキーを削除する
   *
   * @param key キー
   */
  public void remove(String key) {
    session.removeAttribute(key);
  }

  /**
   * セッションに格納されているすべての要素をクリアする
   */
  public void removeAll() {
    // セッションにある全ての要素名を取得する
    Enumeration<String> vals = session.getAttributeNames();

    // 取得した要素名をループ処理で全て削除する
    while (vals.hasMoreElements()) {
      String nm = (String) vals.nextElement();
      remove(nm);
    }
  }

  /**
   * 指定されたセッションが存在するかチェックする
   *
   * @param checkKeys チェックするセッションキー(複数指定可能)
   * @return true:指定したセッションが全て存在する false:存在しないセッションを検知
   */
  public boolean existsSessionKeys(String... checkKeys) {
    // セッションに格納されているキー一覧を取得
    Collection<String> sessionKeys = Collections.list(session.getAttributeNames());
     // 指定されたキーが存在するかチェック
    for (String checkKey : checkKeys) {
      if (!sessionKeys.contains(checkKey)) {
        return false;
      }
    }
    return true;
  }
}
