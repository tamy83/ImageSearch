package com.yensontam.imagesearch;

import com.google.gson.JsonObject;

public class BingImage extends Image {

    private String webSearchUrl;
    private String hostPageUrl;
    // etc...

    BingImage(JsonObject json) {
        super();
        setThumbnailUrl(json.get("thumbnailUrl").getAsString());
        setName(json.get("name").getAsString());
        setUrl(json.get("contentUrl").getAsString());
        setWidth(json.get("width").getAsInt());
        setHeight(json.get("height").getAsInt());
        setThumbnailWidth(((JsonObject) json.get("thumbnail")).get("width").getAsInt());
        setThumbnailHeight(((JsonObject) json.get("thumbnail")).get("height").getAsInt());
        setEncodingFormat(json.get("encodingFormat").getAsString());
    }

    public String getWebSearchUrl() {
        return webSearchUrl;
    }

    public void setWebSearchUrl(String webSearchUrl) {
        this.webSearchUrl = webSearchUrl;
    }

    public String getHostPageUrl() {
        return hostPageUrl;
    }

    public void setHostPageUrl(String hostPageUrl) {
        this.hostPageUrl = hostPageUrl;
    }
}
/**
 * sample json response from Bing
 *{
 * "_type":"Images",
 * "instrumentation":{
 *     "_type":"ResponseInstrumentation"
 * },
 * "readLink":"images\/search?q=tropical ocean",
 * "webSearchUrl":"https:\/\/www.bing.com\/images\/search?q=tropical ocean&FORM=OIIARP",
 * "totalEstimatedMatches":842,
 * "nextOffset":47,
 * "value":[
 *     {
 *         "webSearchUrl":"https:\/\/www.bing.com\/images\/search?view=detailv2&FORM=OIIRPO&q=tropical+ocean&id=8607ACDACB243BDEA7E1EF78127DA931E680E3A5&simid=608027248313960152",
 *         "name":"My Life in the Ocean | The greatest WordPress.com site in ...",
 *         "thumbnailUrl":"https:\/\/tse3.mm.bing.net\/th?id=OIP.fmwSKKmKpmZtJiBDps1kLAHaEo&pid=Api",
 *         "datePublished":"2017-11-03T08:51:00.0000000Z",
 *         "contentUrl":"https:\/\/mylifeintheocean.files.wordpress.com\/2012\/11\/tropical-ocean-wallpaper-1920x12003.jpg",
 *         "hostPageUrl":"https:\/\/mylifeintheocean.wordpress.com\/",
 *         "contentSize":"897388 B",
 *         "encodingFormat":"jpeg",
 *         "hostPageDisplayUrl":"https:\/\/mylifeintheocean.wordpress.com",
 *         "width":1920,
 *         "height":1200,
 *         "thumbnail":{
 *         "width":474,
 *         "height":296
 *         },
 *         "imageInsightsToken":"ccid_fmwSKKmK*mid_8607ACDACB243BDEA7E1EF78127DA931E680E3A5*simid_608027248313960152*thid_OIP.fmwSKKmKpmZtJiBDps1kLAHaEo",
 *         "insightsMetadata":{
 *         "recipeSourcesCount":0,
 *         "bestRepresentativeQuery":{
 *             "text":"Tropical Beaches Desktop Wallpaper",
 *             "displayText":"Tropical Beaches Desktop Wallpaper",
 *             "webSearchUrl":"https:\/\/www.bing.com\/images\/search?q=Tropical+Beaches+Desktop+Wallpaper&id=8607ACDACB243BDEA7E1EF78127DA931E680E3A5&FORM=IDBQDM"
 *         },
 *         "pagesIncludingCount":115,
 *         "availableSizesCount":44
 *         },
 *         "imageId":"8607ACDACB243BDEA7E1EF78127DA931E680E3A5",
 *         "accentColor":"0050B2"
 *     }]
 * }
 */
