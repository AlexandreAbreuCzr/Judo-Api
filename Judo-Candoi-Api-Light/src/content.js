import {
  DEFAULT_WHATSAPP_MESSAGE,
  STATIC_ACHIEVEMENTS,
  STATIC_GALLERY,
  STATIC_METHODOLOGY,
  STATIC_PROGRAMS,
  STATIC_SCHEDULES,
  STATIC_TESTIMONIALS,
  STATIC_TIMELINE
} from "./defaults.js";

function normalizeBlogPost(post) {
  const excerpt = typeof post.excerpt === "string" ? post.excerpt : "";
  const content = typeof post.content === "string" && post.content.trim().length > 0 ? post.content : excerpt;
  const imageUrl = typeof post.imageUrl === "string" && post.imageUrl.trim().length > 0 ? post.imageUrl : null;

  return {
    title: post.title,
    slug: post.slug,
    excerpt,
    content,
    imageUrl
  };
}

function normalizeSponsor(sponsor) {
  return {
    name: sponsor.name,
    description: sponsor.description,
    logoUrl: sponsor.logoUrl,
    websiteUrl: sponsor.websiteUrl
  };
}

function normalizePrideStudent(student) {
  return {
    name: student.name,
    achievement: student.achievement,
    month: student.month,
    imageUrl: student.imageUrl
  };
}

function normalizeSchedule(schedule) {
  return {
    day: typeof schedule?.day === "string" ? schedule.day.trim() : "",
    time: typeof schedule?.time === "string" ? schedule.time.trim() : "",
    audience: typeof schedule?.audience === "string" ? schedule.audience.trim() : ""
  };
}

function normalizeSchedules(schedules) {
  const normalized = Array.isArray(schedules)
    ? schedules
        .map(normalizeSchedule)
        .filter((schedule) => schedule.day && schedule.time && schedule.audience)
    : [];

  if (normalized.length > 0) {
    return normalized;
  }

  return STATIC_SCHEDULES.map(normalizeSchedule);
}

function normalizeGalleryItem(item, index) {
  const imageUrl = typeof item?.imageUrl === "string" ? item.imageUrl.trim() : "";

  if (!imageUrl) {
    return null;
  }

  return {
    title:
      typeof item?.title === "string" && item.title.trim().length > 0
        ? item.title.trim()
        : `Registro de atleta ${String(index + 1).padStart(2, "0")}`,
    imageUrl,
    category:
      typeof item?.category === "string" && item.category.trim().length > 0
        ? item.category.trim()
        : "Atletas"
  };
}

function normalizeGallery(gallery) {
  if (!Array.isArray(gallery)) {
    return STATIC_GALLERY.map((item, index) => normalizeGalleryItem(item, index)).filter(Boolean);
  }

  return gallery.map(normalizeGalleryItem).filter(Boolean);
}

function normalizeTestimonial(item, index) {
  const quote = typeof item?.quote === "string" ? item.quote.trim() : "";

  if (!quote) {
    return null;
  }

  return {
    quote,
    author:
      typeof item?.author === "string" && item.author.trim().length > 0
        ? item.author.trim()
        : `Aluno ${index + 1}`,
    role:
      typeof item?.role === "string" && item.role.trim().length > 0
        ? item.role.trim()
        : "Comunidade Judo Candoi"
  };
}

function normalizeTestimonials(testimonials) {
  if (!Array.isArray(testimonials)) {
    return STATIC_TESTIMONIALS.map((item, index) => normalizeTestimonial(item, index)).filter(Boolean);
  }

  return testimonials.map(normalizeTestimonial).filter(Boolean);
}

export function buildSiteContent(settings, blogPosts, sponsors, prideStudents) {
  const whatsappUrl = `https://wa.me/${settings.whatsappNumber}?text=${encodeURIComponent(
    DEFAULT_WHATSAPP_MESSAGE
  )}`;
  const instagramUserWithoutAt = settings.instagramHandle.replaceAll("@", "");

  return {
    brandName: settings.brandName,
    heroTitle: settings.heroTitle,
    heroSubtitle: settings.heroSubtitle,
    impactPhrase: settings.impactPhrase,
    callToActionPrimaryLabel: settings.callToActionPrimaryLabel,
    callToActionPrimaryUrl: settings.callToActionPrimaryUrl,
    callToActionSecondaryLabel: settings.callToActionSecondaryLabel,
    callToActionSecondaryUrl: whatsappUrl,
    aboutTitle: settings.aboutTitle,
    aboutStory: settings.aboutStory,
    aboutHighlight: settings.aboutHighlight,
    counters: [
      { label: "alunos ativos", value: settings.counterStudents },
      { label: "medalhas conquistadas", value: settings.counterMedals },
      { label: "anos formando atletas", value: settings.counterYears }
    ],
    medalStats: {
      competitions: settings.medalCompetitions,
      fights: settings.medalFights,
      gold: settings.medalGold,
      silver: settings.medalSilver,
      bronze: settings.medalBronze
    },
    programs: STATIC_PROGRAMS,
    methodology: STATIC_METHODOLOGY,
    achievements: STATIC_ACHIEVEMENTS,
    gallery: normalizeGallery(settings.gallery),
    testimonials: normalizeTestimonials(settings.testimonials),
    trialTitle: settings.trialTitle,
    trialDescription: settings.trialDescription,
    schedules: normalizeSchedules(settings.schedules),
    blogPosts: blogPosts.map(normalizeBlogPost),
    sponsors: sponsors.map(normalizeSponsor),
    timeline: STATIC_TIMELINE,
    prideStudents: prideStudents.map(normalizePrideStudent),
    contact: {
      address: settings.academyAddress,
      whatsappUrl,
      whatsappLabel: "WhatsApp direto",
      instagramHandle: settings.instagramHandle,
      instagramUrl: `https://instagram.com/${instagramUserWithoutAt}`,
      mapEmbedUrl: settings.googleMapsEmbed
    },
    finalCallToAction: settings.finalCallToAction
  };
}
