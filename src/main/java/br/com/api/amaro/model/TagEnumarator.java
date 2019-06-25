package br.com.api.amaro.model;

public enum TagEnumarator {
	neutro(0), veludo(1), couro(2), basics(3), festa(4), workwear(5), inverno(6), boho(7), estampas(8), balada(9),
	colorido(10), casual(11), liso(12), moderno(13), passeio(14), metal(15), viagem(16), delicado(17), descolado(18),
	elastano(19);

	public int position;

	TagEnumarator(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public static TagEnumarator getByTag(String tag) {
		for (TagEnumarator tagEnum : TagEnumarator.values()) {
			if (tagEnum.name().toString().toLowerCase().equals(tag.toLowerCase())) {
				return tagEnum;
			}
		}

		return null;
	}

	public static TagEnumarator getByPosition(int position) {
		for (TagEnumarator tagEnum : TagEnumarator.values()) {
			if (tagEnum.position == position) {
				return tagEnum;
			}
		}

		return null;
	}
}
