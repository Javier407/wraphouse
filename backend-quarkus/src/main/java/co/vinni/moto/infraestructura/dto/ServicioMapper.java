package co.vinni.moto.infraestructura.dto;

import co.vinni.moto.dominio.modelo.*;

public class ServicioMapper {

    private ServicioMapper() {}

    public static Servicio dtoAModelo(ServicioDto dto) {
        Diseno diseno = dto.diseno() != null
                ? Diseno.crear(dto.diseno().tipoDiseno(), dto.diseno().nombreReferencia(),
                               dto.diseno().personalizado(), dto.diseno().partes())
                : null;

        PPF ppf = dto.ppf() != null
                ? PPF.crear(dto.ppf().acabado(), dto.ppf().kit(), dto.ppf().partes())
                : null;

        Detailing detailing = dto.detailing() != null
                ? Detailing.crear(dto.detailing().tipo(), dto.detailing().partes())
                : null;

        Protector protector = dto.protector() != null
                ? Protector.crear(dto.protector().acabado(), dto.protector().partes())
                : null;

        return new Servicio(null, dto.motoId(), dto.tipoServicio(), dto.fecha(),
                diseno, ppf, detailing, protector);
    }

    public static ServicioRespuestaDto modeloADto(Servicio modelo) {
        DisenoDto disenoDto = modelo.diseno() != null
                ? new DisenoDto(modelo.diseno().tipoDiseno(), modelo.diseno().nombreReferencia(),
                                modelo.diseno().personalizado(), modelo.diseno().partes())
                : null;

        PPFDto ppfDto = modelo.ppf() != null
                ? new PPFDto(modelo.ppf().acabado(), modelo.ppf().kit(), modelo.ppf().partes())
                : null;

        DetailingDto detailingDto = modelo.detailing() != null
                ? new DetailingDto(modelo.detailing().tipo(), modelo.detailing().partes())
                : null;

        ProtectorDto protectorDto = modelo.protector() != null
                ? new ProtectorDto(modelo.protector().acabado(), modelo.protector().partes())
                : null;

        return new ServicioRespuestaDto(modelo.id(), modelo.motoId(), modelo.tipoServicio(),
                modelo.fecha(), disenoDto, ppfDto, detailingDto, protectorDto);
    }
}
