/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Map;

/**
 *
 * @author Impaler
 */
public class CubeIndex {

	public static final float HALFCUBE = (float) 0.5;
	public static final int MAX_CUBE = 32768;

	//public static final int CUBEBITSHIFT_X = 0;
	//public static final int CUBEBITSHIFT_Y = 5;
	//public static final int CUBEBITSHIFT_Z = 10;
	//public static final int CUBEBITMASK = 31;

	public short Data;  // Index bitpacking   0 ZZZZZ YYYYY XXXXX

	public short DetailLevel;
	public short Size;
	public short Max;
	public short Mask;
	public short Shift;

	public CubeIndex(short DetailLevel) {
		this.DetailLevel = DetailLevel;
		setDetailLevel(DetailLevel);
	}

	public CubeIndex(short DetailLevel, short Data) {
		this.Data = Data;
		setDetailLevel(DetailLevel);
	}

	public final void setDetailLevel(short DetailLevel) {
		this.DetailLevel = DetailLevel;
		this.Shift = (short) ((CubeCoordinate.CELLDETAILLEVELS - DetailLevel) - 1);
		this.Size = (short) (1 << this.Shift);
		
		this.Mask = (short) (this.Size - 1);
		short Xcomponent = (short) (Mask << (this.Shift * 0));
		short Ycomponent = (short) (Mask << (this.Shift * 1));
		short Zcomponent = (short) (Mask << (this.Shift * 2));
		
		this.Max = (short) (Xcomponent | Ycomponent | Zcomponent);
	}

	public CubeIndex(CubeIndex SourceCoords, Direction DirectionType) {
		//X = (short) (SourceCoords.X + DirectionType.getValueonAxis(Axis.AXIS_X));
		//Y = (short) (SourceCoords.Y + DirectionType.getValueonAxis(Axis.AXIS_Y));
		//Z = (short) (SourceCoords.Z + DirectionType.getValueonAxis(Axis.AXIS_Z));
	}

	//public CubeIndex(int NewX, int NewY, int NewZ) {
	//	set(NewX, NewY, NewZ);
	//}

	public void translate(Direction DirectionType) {
		short Xcomponent = (short) ((this.Data >> (this.Shift * 0)) & Mask);
		short Ycomponent = (short) ((this.Data >> (this.Shift * 1)) & Mask);
		short Zcomponent = (short) ((this.Data >> (this.Shift * 2)) & Mask);

		Xcomponent += DirectionType.getValueonAxis(Axis.AXIS_X);
		Ycomponent += DirectionType.getValueonAxis(Axis.AXIS_Y);
		Zcomponent += DirectionType.getValueonAxis(Axis.AXIS_Z);
		
		if ((Xcomponent / Size) == 0) {
			Xcomponent = 0;
			Ycomponent++;
			if ((Ycomponent / Size) == 0) {
				Ycomponent = 0;
				Zcomponent++;
				if ((Zcomponent / Size) == 0) {
					Zcomponent = 0;
				}
			}	
		}

		Xcomponent = (short) (Xcomponent << (this.Shift * 0));
		Ycomponent = (short) (Ycomponent << (this.Shift * 1));
		Zcomponent = (short) (Zcomponent << (this.Shift * 2));

		Data = (short) (Xcomponent | Ycomponent | Zcomponent);
	}

	public void translate(Direction DirectionType, int Length) {
		short Xcomponent = (short) ((this.Data >> (this.Shift * 0)) & Mask);
		short Ycomponent = (short) ((this.Data >> (this.Shift * 1)) & Mask);
		short Zcomponent = (short) ((this.Data >> (this.Shift * 2)) & Mask);

		Xcomponent += DirectionType.getValueonAxis(Axis.AXIS_X) * Length;
		Ycomponent += DirectionType.getValueonAxis(Axis.AXIS_Y) * Length;
		Zcomponent += DirectionType.getValueonAxis(Axis.AXIS_Z) * Length;

		Xcomponent %= this.Size;
		Ycomponent %= this.Size;
		Zcomponent %= this.Size;
		
		Xcomponent = (short) (Xcomponent << (this.Shift * 0));
		Ycomponent = (short) (Ycomponent << (this.Shift * 1));
		Zcomponent = (short) (Zcomponent << (this.Shift * 2));

		Data = (short) (Xcomponent | Ycomponent | Zcomponent);
	}

	public void set(int NewX, int NewY, int NewZ) {
		short Xcomponent = (short) (NewX & Mask);
		short Ycomponent = (short) (NewY & Mask);
		short Zcomponent = (short) (NewZ & Mask);

		Xcomponent = (short) (Xcomponent << (this.Shift * 0));
		Ycomponent = (short) (Ycomponent << (this.Shift * 1));
		Zcomponent = (short) (Zcomponent << (this.Shift * 2));

		Data = (short) (Xcomponent | Ycomponent | Zcomponent);
	}

	public void set(Axis AxialComponent, int NewValue) {
		short Xcomponent = (short) ((this.Data >> (this.Shift * 0)) & Mask);
		short Ycomponent = (short) ((this.Data >> (this.Shift * 1)) & Mask);
		short Zcomponent = (short) ((this.Data >> (this.Shift * 2)) & Mask);

		switch (AxialComponent) {
			case AXIS_Z:
				Zcomponent = (short) (NewValue & Mask);
				break;
			case AXIS_Y:
				Ycomponent = (short) (NewValue & Mask);
				break;
			case AXIS_X:
				Xcomponent = (short) (NewValue & Mask);
				break;

			default:
				break;
		}
		Xcomponent = (short) (Xcomponent << (this.Shift * 0));
		Ycomponent = (short) (Ycomponent << (this.Shift * 1));
		Zcomponent = (short) (Zcomponent << (this.Shift * 2));

		Data = (short) (Xcomponent | Ycomponent | Zcomponent);		
	}

	public short getCubeIndex() {
		//short Xcomponent = (short) ((this.Data >> CUBEBITSHIFT_X) & CUBEBITMASK);
		//short Ycomponent = (short) ((this.Data >> CUBEBITSHIFT_Y) & CUBEBITMASK);
		//short Zcomponent = (short) ((this.Data >> CUBEBITSHIFT_Z) & CUBEBITMASK);

		return Data;
	}
	
	public short getX() {
		return (short) ((this.Data >> (this.Shift * 0)) & Mask);
	}

	public short getY() {
		return (short) ((this.Data >> (this.Shift * 1)) & Mask);
	}

	public short getZ() {
		return (short) ((this.Data >> (this.Shift * 2)) & Mask);
	}

	public void next() {
		//short Xcomponent = (short) ((this.Data >> CUBEBITSHIFT_X) & CUBEBITMASK);
		//short Ycomponent = (short) ((this.Data >> CUBEBITSHIFT_Y) & CUBEBITMASK);
		//short Zcomponent = (short) ((this.Data >> CUBEBITSHIFT_Z) & CUBEBITMASK);

		Data++;
		//consolidate();
	}
	
	public boolean end() {
		return (Data > this.Max || Data < 0);
	}
	/*
	private void consolidate() {
		short Xcomponent = (short) ((this.Data >> (this.Shift * 0)) & Mask);
		short Ycomponent = (short) ((this.Data >> (this.Shift * 1)) & Mask);
		short Zcomponent = (short) ((this.Data >> (this.Shift * 2)) & Mask);

		if (Xcomponent >= Size) {
			Xcomponent = 0;
			Ycomponent++;
			if (Ycomponent >= Size) {
				Ycomponent = 0;
				Zcomponent++;
				if (Zcomponent >= Size) {
					Zcomponent = 0;
				}
			}
		}

		Xcomponent = (short) (Xcomponent << (this.Shift * 0));
		Ycomponent = (short) (Ycomponent << (this.Shift * 1));
		Zcomponent = (short) (Zcomponent << (this.Shift * 2));
		
		Data = (short) (Xcomponent | Ycomponent | Zcomponent);		
	}*/

	public void copy(CubeIndex ArgumentCoordinates) {
		this.Data = ArgumentCoordinates.Data;
		setDetailLevel(ArgumentCoordinates.DetailLevel);
	}

	@Override
	public CubeIndex clone() {
		return new CubeIndex(Data, DetailLevel);
	}

	@Override
	public boolean equals(Object ArgumentCoordinates) {

		//if (ArgumentCoordinates == null)
		//return false;
		//if (ArgumentCoordinates == this)
		//return true;
		//if (!(ArgumentCoordinates instanceof MapCoordinate))
		//return false;

		CubeIndex Arg = (CubeIndex) ArgumentCoordinates;
		return (Arg.Data == this.Data && Arg.DetailLevel == this.DetailLevel);
	}

	public int getValueonAxis(Axis AxialComponent) {
		switch (AxialComponent) {
			case AXIS_X:
				return (short) ((this.Data >> (this.Shift * 0)) & Mask);
			case AXIS_Y:
				return (short) ((this.Data >> (this.Shift * 1)) & Mask);
			case AXIS_Z:
				return (short) ((this.Data >> (this.Shift * 2)) & Mask);
			default:
				return 0;
		}
	}

	@Override
	public int hashCode() {
		//int hash = 3;
		//hash += 17 * X;
		//hash += 37 * Y;
		//hash += 5 * Z;
		return Data;
	}
}
