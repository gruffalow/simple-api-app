htmx.defineExtension('json-enc', {
    encodeParameters : function(xhr, parameters, elt) {
        xhr.overrideMimeType('text/json');
        return (JSON.stringify(parameters));
    },
    overrideHeaders : function(headers) {
        headers['Content-Type'] = 'application/json';
    }
});