import java.applet.*;
import java.awt.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.applet.*;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;

public class PrimitiveTest extends Applet{
	private void createPrimitives(TransformGroup tg) {
		// 以下の4行によって物体の見た目を設定する
		Appearance appearance = new Appearance();
		Material material = new Material();
		material.setDiffuseColor(1.0f, 0.0f, 0.0f);
		appearance.setMaterial(material);
		
		// 円錐を作成し、tgに接続する
		Cone cone = new Cone(0.5f, 1.0f, Primitive.GENERATE_NORMALS, 100, 10, appearance);
		tg.addChild(cone);
	}
	
	public PrimitiveTest(){
		// 描画領域であるCanvas3Dを作成し、Appletウィンドウの内部に配置する
		Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		this.setLayout(new BorderLayout());
		this.add(canvas3D, BorderLayout.CENTER);
		
		// SimpleUniverseを作成し、標準的な投影変換を行うように設定する
		SimpleUniverse simpleUniverse = new SimpleUniverse(canvas3D);
		simpleUniverse.getViewingPlatform().setNominalViewingTransform();
		
		// BranchGroupを作成する。このメソッドの最後でSimpleUniverseに接続する
		// 以下のプログラムではbgの下にシーングラフのノードを接続していく。
		BranchGroup bg = new BranchGroup();
		
		// 平行光源を作成し、bgに接続する
		DirectionalLight directionalLight = new DirectionalLight();
		directionalLight.setInfluencingBounds(new BoundingSphere());
		bg.addChild(directionalLight);
		
		// シーン全体の座標変換を行うTransformGroupを作成する。
		TransformGroup tg = new TransformGroup();
		
		// マウスによる操作を行うために、MouseRotateクラスのインスタンスを作成し、
		// tgに接続する
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		MouseRotate mouseRotate = new MouseRotate(tg);
		mouseRotate.setSchedulingBounds(new BoundingSphere());
		tg.addChild(mouseRotate);
		MouseTranslate mouseTranslate = new MouseTranslate(tg);
		mouseTranslate.setSchedulingBounds(new BoundingSphere());
		tg.addChild(mouseTranslate);
		MouseZoom mouseZoom = new MouseZoom(tg);
		mouseZoom.setSchedulingBounds(new BoundingSphere());
		tg.addChild(mouseZoom);

		// createPrimtivesメソッドを呼び出しtgに接続させる
		createPrimitives(tg);
		
		// tgへの接続がすべて終わったので、bgに接続する
		bg.addChild(tg);

		// SimpleUniverseにBranchGroupを接続する
		simpleUniverse.addBranchGraph(bg);
	}

	
	public static void main(String[] args){		
		new MainFrame(new PrimitiveTest(), 640, 480);
	}
}
