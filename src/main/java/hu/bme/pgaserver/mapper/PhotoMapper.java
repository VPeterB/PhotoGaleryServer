package hu.bme.pgaserver.mapper;

import hu.bme.pgaserver.dto.PhotoDTO;
import hu.bme.pgaserver.model.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoMapper {

    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);

    PhotoDTO photoToPhotoDto(Photo photo);

    Photo photoDtoToPhoto(PhotoDTO photoDto);
}
